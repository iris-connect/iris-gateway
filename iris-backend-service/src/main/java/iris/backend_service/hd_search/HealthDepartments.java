package iris.backend_service.hd_search;

import java.util.List;
import java.util.Optional;

import org.xmlbeam.annotation.XBDocURL;
import org.xmlbeam.annotation.XBRead;

@XBDocURL("resource://masterdata/TransmittingSiteSearchText.xml")
public interface HealthDepartments {

	// Without NamespacePhilosophy.AGNOSTIC the namespace of an element must be declared!
	// @XBRead("/xbdefaultns:TransmittingSites/xbdefaultns:TransmittingSite")
	@XBRead("/TransmittingSites/TransmittingSite")
	List<HealthDepartment> getAll();

	@XBRead("//SearchText[@Value='{0}']/..")
	Optional<HealthDepartment> findDepartmentWithExact(String searchString);

	@XBRead("boolean(//SearchText[@Value='{0}'])")
	boolean hasDepartmentWithExact(String searchString);

	@XBRead("//SearchText[contains(@Value, '{0}')]/..")
	List<HealthDepartment> findDepartmentsContains(String searchString);

	public interface HealthDepartment {
		/**
		 * @return Often the administrative unit (e.g. the county)
		 */
		@XBRead("@Name")
		String getName();

		/**
		 * @return The code from RKI
		 */
		@XBRead("@Code")
		String getCode();

		/**
		 * @return The department of the administrative unit.
		 */
		@XBRead("@Department")
		String getDepartment();

		@XBRead(".")
		Address getAddress();

		@XBRead("@Phone")
		String getPhone();

		@XBRead("@Fax")
		String getFax();

		@XBRead("@Email")
		String getEmail();

		@XBRead(".")
		Covid19ContactData getCovid19ContactData();

		@XBRead(".")
		EinreiseContactData getEinreiseContactData();

		@XBRead("SearchText/@Value")
		List<String> getSearchTexts();

		public interface Address {
			@XBRead("@Street")
			String getStreet();

			@XBRead("@Postalcode")
			String getZipCode();

			@XBRead("@Place")
			String getPlace();
		}

		public interface Covid19ContactData {

			@XBRead("@Covid19Hotline")
			String getHotline();

			@XBRead("@Covid19Fax")
			String getFax();

			@XBRead("@Covid19EMail")
			String getEmail();
		}

		public interface EinreiseContactData {

			@XBRead("@EinreiseAnmeldungHotline")
			String getHotline();

			@XBRead("@EinreiseAnmeldungFax")
			String getFax();

			@XBRead("@EinreiseAnmeldungEMail")
			String getEmail();
		}
	}
}
