package de.healthIMIS.iris.dummy_app;

import static org.springframework.hateoas.client.Hop.rel;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.digest.DigestUtils;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;
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

	private final LinkDiscoverer discoverer = new HalLinkDiscoverer();
	private final RestTemplate template = new RestTemplate();
	private final ObjectMapper mapper = new ObjectMapper();
	private final TextIO textIO = TextIoFactory.getTextIO();
	private Traverson traverson;

	/**
	 * Starts application and handles command line options/parameters.
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {

		var options = new Options();
		options.addOption(new Option("h", "help", false, "print this message"));
		options.addOption(Option.builder("a").longOpt("address").desc("the address of the API (default = http://localhost:8090)").hasArg().build());
		options.addOption(Option.builder("n").longOpt("name").desc("name of the user (default = Max Muster)").hasArg().build());
		options.addOption(Option.builder("b").longOpt("birth").desc("date of birth of the user (default = 1990-01-01)").hasArg().build());
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
		var address = cmd.getOptionValue("a", "http://localhost:8090");
		var name = cmd.getOptionValue("n", "Max Muster");
		var dateOfBirth = LocalDate.parse(cmd.getOptionValue("b", "1990-01-01"));

		new IrisDummyApp(debug, address, name, dateOfBirth).run();
	}

	/**
	 * Instantiates the helper library and starts the input cycle.
	 */
	private void run() {

		traverson = new Traverson(URI.create(address), MediaTypes.HAL_JSON);

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
		var getByCodeHop = rel("GetDataRequestByCode").withParameter("code", code);

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
				postSubmission(link, content);

			} else if (link.hasRel("PostEventsSubmission")) {
				postSubmission(link, createEvents());
			} else if (link.hasRel("PostGuestsSubmission")) {
				postSubmission(link, createGuests());
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
	 * Send a POST request for the given data submission to the given Link to the API.
	 * 
	 * @param template
	 * @param textIO
	 *            TODO
	 * @param link
	 * @param content
	 */
	private void postSubmission(Link link, String content) {

		var salt = "saltX";
		var keyReferenz = "keyX";

		var submission = new DataSubmissionDto().checkCode(determineCheckcode()).salt(salt).keyReferenz(keyReferenz).encryptedData(content);

		textIO.getTextTerminal().printf("\nData submission is sent to healt department with salt '%s' and key referenz '%s'", salt, keyReferenz);
		if (debug) {
			textIO.getTextTerminal().printf("\nContent of the data submission unencrypted:\n %s", content);
		}
		textIO.getTextTerminal().printf("\n\n");

		template.postForObject(link.getHref(), submission, DataRequestDto.class);
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
