package de.healthIMIS.iris.dummy_app;

import java.net.URI;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Level;
import de.healthIMIS.iris.dummy_app.submissions.ContactPerson;
import de.healthIMIS.iris.dummy_app.submissions.ContactPersonList;
import de.healthIMIS.iris.dummy_app.submissions.DataSubmissionDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IrisDummyApp {

	/**
	 * is debug output enable
	 */
	private final boolean debug;
	/**
	 * The address of the API
	 */
	private final String address;
	/**
	 * Name of the user - is used for check code
	 */
	private final String name;
	/**
	 * Date of birth of the user - is used for check code
	 */
	private final LocalDate dateOfBirth;
	/**
	 * Random Checkcode - is used as fallback
	 */
	private final String randomCode;

	private final LinkDiscoverer discoverer = new HalLinkDiscoverer();
	private final ObjectMapper mapper = new ObjectMapper();
	private final TextIO textIO = TextIoFactory.getTextIO();
	private RestTemplate rest;
	private Traverson traverson;

	/**
	 * Starts application and handles command line options/parameters.
	 * 
	 * @param args
	 * @throws ParseException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static void main(String[] args) throws ParseException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		var options = new Options();
		options.addOption(new Option("h", "help", false, "print this message"));
		options.addOption(Option.builder("a").longOpt("address").desc("the address of the API (default = http://localhost:8090)").hasArg().build());
		options.addOption(Option.builder("n").longOpt("name").desc("name of the user (default = Max Muster)").hasArg().build());
		options.addOption(Option.builder("b").longOpt("birth").desc("date of birth of the user (default = 1990-01-01)").hasArg().build());
		options.addOption(
			Option.builder("r")
				.longOpt("random")
				.desc("random checkcode - can be fixed for development in the client (default = ABCDEFGHKL)")
				.hasArg()
				.build());
		options.addOption(Option.builder("d").longOpt("debug").desc("enable debug output").build());

		var parser = new DefaultParser();
		var cmd = parser.parse(options, args);

		// help
		if (cmd.hasOption('h')) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("iris-dummy-app", options);

			return;
		}

		// debug
		var debug = cmd.hasOption('d');
		if (debug) {
			ch.qos.logback.classic.Logger root =
				(ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
			root.setLevel(Level.DEBUG);
		}

		// functional options
		var address = cmd.getOptionValue("a", "https://localhost:8443");
		var name = cmd.getOptionValue("n", "Max Muster");
		var dateOfBirth = LocalDate.parse(cmd.getOptionValue("b", "1990-01-01"));
		var randomCode = cmd.getOptionValue("r", "ABCDEFGHKL");

		new IrisDummyApp(debug, address, name, dateOfBirth, randomCode).run();
	}

	/**
	 * Instantiates the helper library, disables SSL trust check for development and starts the input cycle.
	 * 
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private void run() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		var sslContext = new SSLContextBuilder().loadTrustMaterial((chain, authType) -> true) // trust all server certificates
			.build();

		var socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		var httpClient = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();

		rest = new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));

		traverson = new Traverson(URI.create(address), MediaTypes.HAL_JSON);
		traverson.setRestOperations(rest);

		textIO.getTextTerminal().printf("\nAPI-Address: %s; User name: %s; Date of Birth: %s\n\n", address, name, dateOfBirth);

		while (true) {
			cycle();
		}
	}

	/**
	 * Performs a cycle from entering the code, fetching the data request, entering the requested data and submitting the data submission to
	 * the server.
	 */
	private void cycle() {

		// read the code
		var code = textIO.newStringInputReader()
			.withDefaultValue("790b9a69-17f8-4ba7-a8ae-2f7bf34e0b80")
			.withPattern("[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}")
			.read("Code");

		// get data request object
		var getByCodeHop = Hop.rel("GetDataRequestByCode").withParameter("code", code);

		var dataRequest = traverson.follow(getByCodeHop).toObject(DataRequestDto.class);

		textIO.getTextTerminal()
			.printf(
				"\nData request from healt department %s for the period %s to %s\n\n",
				dataRequest.getHealthDepartment(),
				dataRequest.getStart(),
				dataRequest.getEnd());

		// determines the existing links for the next POST operation 
		var response = traverson.follow(getByCodeHop).toObject(String.class);

		var postContacts = discoverer.findLinkWithRel("PostContactsSubmission", response);
		var postEvents = discoverer.findLinkWithRel("PostEventsSubmission", response);
		var postGuests = discoverer.findLinkWithRel("PostGuestsSubmission", response);

		var links = Stream.of(postContacts, postEvents, postGuests).flatMap(Optional<Link>::stream).collect(Collectors.toList());

		if (links.isEmpty()) {
			// exit the method if there is no link 
			return;
		}

		var link = determineNextPostOperation(links);

		// entering the requested data and submitting the data submission to the server
		try {

			if (link.hasRel("PostContactsSubmission")) {

				var content = mapper.writeValueAsString(createContacts());
				content = encryptContent(content, dataRequest.getKeyOfHealthDepartment());
				postSubmission(link, content, dataRequest.getKeyReferenz());

			} else if (link.hasRel("PostEventsSubmission")) {
				postSubmission(link, createEvents(), dataRequest.getKeyReferenz());
			} else if (link.hasRel("PostGuestsSubmission")) {
				postSubmission(link, createGuests(), dataRequest.getKeyReferenz());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lets the user enter the data for contacts and creates a DTO from it.
	 * 
	 * @param textIO
	 * @return
	 */
	private ContactPersonList createContacts() {

		ContactPersonList list = new ContactPersonList();

		do {
			var contactPerson = new ContactPerson();

			contactPerson.firstName(textIO.newStringInputReader().read("First name"));
			contactPerson.lastName(textIO.newStringInputReader().read("Last name"));

			list.addContactPersonsItem(contactPerson);
		}
		while (textIO.newBooleanInputReader().withDefaultValue(Boolean.TRUE).read("More contacts?"));

		return list;
	}

	/**
	 * Lets the user enter the data for events and creates a DTO from it.
	 * 
	 * @param textIO
	 * @return
	 */
	private String createEvents() {

		return textIO.newStringInputReader().withDefaultValue("Data").read("Content");
	}

	/**
	 * Lets the user enter the data for guests and creates a DTO from it.
	 * 
	 * @param textIO
	 * @return
	 */
	private String createGuests() {

		return textIO.newStringInputReader().withDefaultValue("Data").read("Content");
	}

	/**
	 * Encrypts the content with the given key from data request.
	 * 
	 * @param content
	 * @param keyOfHealthDepartment
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private String encryptContent(String content, String keyOfHealthDepartment)
		throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException,
		BadPaddingException {

		var keyBytes = Base64.getDecoder().decode(keyOfHealthDepartment);

		var kf = KeyFactory.getInstance("RSA");
		var publicKey = kf.generatePublic(new X509EncodedKeySpec(keyBytes));

		var cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		String klarText = content;
		byte[] encryptedArray = cipher.doFinal(klarText.getBytes());

		var ret = Base64.getEncoder().encodeToString(encryptedArray);

		if (debug) {
			textIO.getTextTerminal().printf("Text to encrypt:           %s\n\n", klarText);
			textIO.getTextTerminal().printf("Text encrypted:            %s\n\n", ret);
		}

		return ret;
	}

	/**
	 * Send a POST request for the given data submission to the given Link to the API.
	 * 
	 * @param template
	 * @param textIO
	 * @param link
	 * @param content
	 * @param keyReferenz
	 */
	private void postSubmission(Link link, String content, String keyReferenz) {

		var submission = new DataSubmissionDto().checkCode(determineCheckcode()).keyReferenz(keyReferenz).encryptedData(content);

		textIO.getTextTerminal().printf("\nData submission is sent to healt department with key referenz '%s'", keyReferenz);
		if (debug) {
			textIO.getTextTerminal().printf("\nContent of the data submission unencrypted:\n %s", content);
		}
		textIO.getTextTerminal().printf("\n\n");

		rest.postForObject(link.getHref(), submission, DataRequestDto.class);
	}

	/**
	 * Determines the check codes from the user data name and date of birth.
	 * 
	 * @return
	 */
	private List<String> determineCheckcode() {

		var ret = new ArrayList<String>();

		if (name != null) {

			var nameMod = name.toLowerCase().replaceAll("[^\\pL\\pN]", "");
			var nameHash = DigestUtils.md5Hex(nameMod);
			ret.add(nameHash);

			if (debug) {
				textIO.getTextTerminal().printf("\nCheck code for name is MD5 of '%s' = '%s'\n\n", nameMod, nameHash);
			}
		}

		if (dateOfBirth != null) {

			var date = dateOfBirth.getYear() * 10000 + dateOfBirth.getMonthValue() * 100 + dateOfBirth.getDayOfMonth();
			var dateHash = DigestUtils.md5Hex(Integer.toString(date));
			ret.add(dateHash);

			if (debug) {
				textIO.getTextTerminal().printf("\nCheck code for date of birth is MD5 of '%s' = '%s'\n\n", date, dateHash);
			}
		}

		if (randomCode != null) {
			ret.add(randomCode);
		}

		return ret;
	}

	/**
	 * Identifies the possible functions from the links and lets the user select one if there is more than one.
	 * 
	 * @param textIO
	 * @param options
	 * @return
	 */
	private Link determineNextPostOperation(List<Link> options) {

		if (options.size() > 1) {
			return textIO.<Link> newGenericInputReader(null)
				.withNumberedPossibleValues(options)
				.withValueFormatter(Link::getTitle)
				.read("What will you push?");
		}

		return options.get(0);
	}
}
