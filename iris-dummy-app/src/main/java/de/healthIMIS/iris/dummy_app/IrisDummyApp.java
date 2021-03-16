package de.healthIMIS.iris.dummy_app;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;
import static org.apache.commons.codec.digest.DigestUtils.md5;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.binary.Base64;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Level;
import de.healthIMIS.iris.dummy_app.submissions.ContactPerson;
import de.healthIMIS.iris.dummy_app.submissions.ContactPersonList;
import de.healthIMIS.iris.dummy_app.submissions.ContactsAndEvents;
import de.healthIMIS.iris.dummy_app.submissions.DataSubmissionDto;
import de.healthIMIS.iris.dummy_app.submissions.Event;
import de.healthIMIS.iris.dummy_app.submissions.EventList;
import de.healthIMIS.iris.dummy_app.submissions.Guest;
import de.healthIMIS.iris.dummy_app.submissions.GuestList;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IrisDummyApp {

	private static final String GUESTS_REL = "PostGuestsSubmission";
	private static final String CONTACTS_EVENTS_REL = "PostContactsEventsSubmission";

	static final String ENCRYPTION_ALGORITHM = "AES";
	static final int ENCRYPTION_KEY_LENGTH = 256;
	static final String KEY_ENCRYPTION_ALGORITHM = "RSA";
	static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

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
	private final boolean useTeleCode;
	private final Properties properties;

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
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParseException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException {

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
		options.addOption(Option.builder("t").longOpt("telecode").desc("uses a teleCode instead of a code").build());
		options.addOption(Option.builder("f").longOpt("inputfile").desc("properties file with data for automatic input").hasArg().build());

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
		var useTeleCode = cmd.hasOption('t');

		var file = cmd.getOptionValue('f');
		var properties = new Properties();
		var stream = new BufferedInputStream(new FileInputStream(file));
		properties.load(stream);
		stream.close();

		new IrisDummyApp(debug, address, name, dateOfBirth, randomCode, useTeleCode, properties).run();
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

		var hop = useTeleCode ? getHopByTeleCode() : getHopByCode();

		var dataRequest = traverson.follow(hop).toObject(DataRequestDto.class);

		textIO.getTextTerminal()
			.printf(
				"\nData request from healt department %s for the period %s to %s\n\n",
				dataRequest.getHealthDepartment(),
				dataRequest.getStart(),
				dataRequest.getEnd());

		// determines the existing links for the next POST operation 
		var response = traverson.follow(hop).toObject(String.class);

		var postContactsEvents = discoverer.findLinkWithRel(CONTACTS_EVENTS_REL, response);
		var postGuests = discoverer.findLinkWithRel(GUESTS_REL, response);

		var links = Stream.of(postContactsEvents, postGuests).flatMap(Optional<Link>::stream).collect(Collectors.toList());

		if (links.isEmpty()) {
			// exit the method if there is no link 
			return;
		}

		var link = determineNextPostOperation(links);

		try {

			// generates the key for data transmission and encrypts it with the public key of the health department
			var kf = KeyFactory.getInstance(KEY_ENCRYPTION_ALGORITHM);
			var keyBytes = decodeBase64(dataRequest.getKeyOfHealthDepartment());
			var publicKey = kf.generatePublic(new X509EncodedKeySpec(keyBytes));

			var secretKey = generateSymmetricKey();
			var encryptedSecretKey = encodeBase64String(encryptSecretKey(secretKey, publicKey));

			// entering the requested data and submitting the data submission to the server
			Object contentObject = null;

			if (link.hasRel(CONTACTS_EVENTS_REL)) {
				contentObject = createContactsAndEvents();
			} else if (link.hasRel(GUESTS_REL)) {
				contentObject = createGuests();
			}

			if (contentObject != null) {

				var content = mapper.writeValueAsString(contentObject);
				content = encryptContent(content, secretKey);
				postSubmission(link, content, encryptedSecretKey, dataRequest.getKeyReferenz());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	private Hop getHopByTeleCode() {

		// read the teleCode
		var teleCode = textIO.newStringInputReader().withPattern("[A-HKMNP-Z1-9]{9}[A-F1-9]").read("TeleCode");

		// read the ZIP
		var zip = textIO.newStringInputReader().withPattern("[0-9]{5}").read("ZIP");

		// get data request object
		return Hop.rel("GetDataRequestByTeleCode").withParameter("teleCode", teleCode).withParameter("zip", zip);
	}

	/**
	 * @return
	 */
	private Hop getHopByCode() {

		// read the code
		var code = textIO.newStringInputReader()
			.withDefaultValue("790b9a69-17f8-4ba7-a8ae-2f7bf34e0b80")
			.withPattern("[a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8}")
			.read("Code");

		// get data request object
		return Hop.rel("GetDataRequestByCode").withParameter("code", code);
	}

	/**
	 * Lets the user enter the data for contacts and events and creates a DTO from it.
	 * 
	 * @return
	 */
	private ContactsAndEvents createContactsAndEvents() {

		var contactList = new ContactPersonList();
		var eventList = new EventList();

		if (properties == null) {

			ContactsEvents selection;
			while ((selection = textIO.newEnumInputReader(ContactsEvents.class).withAllValuesNumbered().read("Select …")) != ContactsEvents.Stop) {

				if (selection == ContactsEvents.Contacts) {
					contactList.addContactPersonsItem(createContact());
				} else if (selection == ContactsEvents.Events) {
					eventList.addEventsItem(createEvent());
				}
			}

		} else {
			readContactsAndEvents(contactList, eventList);
		}

		return new ContactsAndEvents().contacts(contactList).events(eventList);
	}

	/**
	 * Read contacts and events from properties file.
	 * 
	 * @param contactList
	 * @param eventList
	 */
	private void readContactsAndEvents(ContactPersonList contactList, EventList eventList) {

		for (int i = 0; properties.containsKey("contact." + i + ".lastName"); i++) {

			var contactPerson = new ContactPerson();
			contactPerson.firstName(properties.getProperty("contact." + i + ".firstName"));
			contactPerson.lastName(properties.getProperty("contact." + i + ".lastName"));
			contactList.addContactPersonsItem(contactPerson);
		}

		for (int i = 0; properties.containsKey("event." + i + ".title"); i++) {

			var event = new Event();
			event.name(properties.getProperty("event." + i + ".title"));
			event.additionalInformation(properties.getProperty("event." + i + ".infos"));
			eventList.addEventsItem(event);
		}
	}

	/**
	 * Lets the user enter the data for a contact.
	 * 
	 * @return
	 */
	private ContactPerson createContact() {

		var contactPerson = new ContactPerson();

		contactPerson.firstName(textIO.newStringInputReader().read("First name"));
		contactPerson.lastName(textIO.newStringInputReader().read("Last name"));

		return contactPerson;
	}

	/**
	 * Lets the user enter the data for an event.
	 * 
	 * @return
	 */
	private Event createEvent() {

		var event = new Event();

		event.name(textIO.newStringInputReader().read("Event name"));
		event.additionalInformation(textIO.newStringInputReader().read("Additional informations"));

		return event;
	}

	/**
	 * Lets the user enter the data for guests and creates a DTO from it.
	 * 
	 * @return
	 */
	private GuestList createGuests() {

		var list = new GuestList();

		if (properties == null) {

			do {
				var guest = new Guest();

				guest.firstName(textIO.newStringInputReader().read("First name"));
				guest.lastName(textIO.newStringInputReader().read("Last name"));

				list.addGuestsItem(guest);
			}
			while (textIO.newBooleanInputReader().withDefaultValue(Boolean.TRUE).read("More guests?"));

		} else {

			for (int i = 0; properties.containsKey("guest." + i + ".lastName"); i++) {

				var guest = new Guest();
				guest.firstName(properties.getProperty("guest." + i + ".firstName"));
				guest.lastName(properties.getProperty("guest." + i + ".lastName"));
				list.addGuestsItem(guest);
			}
		}

		return list;
	}

	/**
	 * Encrypts the content with the given key from data request.
	 */
	private String encryptContent(String content, SecretKey secretKey)
		throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException,
		BadPaddingException {

		var encryptedArray = encryptText(content, secretKey);

		var ret = Base64.encodeBase64String(encryptedArray);

		if (debug) {
			textIO.getTextTerminal().printf("Text to encrypt:           %s\n\n", content);
			textIO.getTextTerminal().printf("Text encrypted:            %s\n\n", ret);
		}

		return ret;
	}

	SecretKey generateSymmetricKey() throws NoSuchAlgorithmException {

		var generator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
		generator.init(ENCRYPTION_KEY_LENGTH);

		return generator.generateKey();
	}

	byte[] encryptText(String textToEncrypt, SecretKey secretKey)
		throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

		var cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		return cipher.doFinal(textToEncrypt.getBytes(UTF_8));
	}

	byte[] encryptSecretKey(SecretKey secretKey, PublicKey publicKey)
		throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

		var cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.PUBLIC_KEY, publicKey);

		return cipher.doFinal(secretKey.getEncoded());
	}

	/**
	 * Send a POST request for the given data submission to the given Link to the API.
	 */
	private void postSubmission(Link link, String content, String encryptedSecretKey, String keyReferenz) {

		var submission =
			new DataSubmissionDto().checkCode(determineCheckcode()).keyReferenz(keyReferenz).secret(encryptedSecretKey).encryptedData(content);

		textIO.getTextTerminal().printf("\nData submission is sent to healt department with key referenz '%s'", keyReferenz);
		if (debug) {
			textIO.getTextTerminal().printf("\nContent of the data submission unencrypted:\n %s", content);
		}
		textIO.getTextTerminal().printf("\n\n");

		var headers = new HttpHeaders();
		headers.setContentType(new MediaType(APPLICATION_JSON, UTF_8));

		rest.postForObject(link.getHref(), new HttpEntity<>(submission, headers), DataRequestDto.class);
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
			var nameHash = encodeBase64String(md5(nameMod));
			ret.add(nameHash);

			if (debug) {
				textIO.getTextTerminal().printf("\nCheck code for name is MD5 of '%s' = '%s'\n\n", nameMod, nameHash);
			}
		}

		if (dateOfBirth != null) {

			var date = dateOfBirth.getYear() * 10000 + dateOfBirth.getMonthValue() * 100 + dateOfBirth.getDayOfMonth();
			var dateHash = encodeBase64String(md5(Integer.toString(date)));
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

	enum ContactsEvents {
		Contacts,
		Events,
		Stop
	}
}
