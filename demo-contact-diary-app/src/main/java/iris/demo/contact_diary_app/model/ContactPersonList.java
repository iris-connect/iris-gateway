/*
 * IRIS-Gateway API
 * ### Encryption of the data to be transmitted (contact data) In order to be not limited in the amount of data, a hybrid encryption with symmetric encryption of the data and asymmetric encryption of the symmetric key is used for the encryption of the contact data.    1. The apps and applications get the public key of the health department as a 4096-bit RSA key from the IRIS+ server. This key is base64-encoded similar to the Private Enhanced Mail (PEM) format but without key markers (-----BEGIN PUBLIC KEY----- / -----END PUBLIC KEY-----).   2. The app generates a 256-bit AES key.   3. The data is encrypted with this key (algorithm: AES/CBC/PKCS5Padding and 16 byte IV)   4. IV bytes are prepended to the cipher text. Those merged bytes represent the encrypted content.   5. The AES key must be encrypted with the public RSA key of the health department. (algorithm: RSA with Optimal Asymmetric Encryption Padding (OAEP) \"RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING\")   6. The encrypted AES key and the encrypted content must be transmitted base64 encoded.    #### Schematic sequence    ```   pubKeyEncryption = publicKeyFromBase64(givenPublicKey);   contentKey = generateAESKey();   iv = generateRandomBytes(16);    encrypted = contentKey.encrypt(content, \"AES/CBC/PKCS5Padding\", iv);   keyEncrypted = pubKeyEncryption.encrypt(contentKey, \"RSA/NONE/OAEPWithSHA3-256AndMGF1Padding\");    submissionDto.encryptedData = base64Encode(concat(iv,encrypted));   submissionDto.secret = base64Encode(keyEncrypted);   ``` 
 *
 * The version of the OpenAPI document: 0.2.0
 * Contact: jens.kutzsche@gebea.de
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package iris.demo.contact_diary_app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A collection of contact persons who had contact with the queried person during the queried time. This data must be
 * encrypted with the key of health department from DataRequest.keyOfHealthDepartment and must be encoded with Base64!
 */
public class ContactPersonList {
	public static final String SERIALIZED_NAME_CONTACT_PERSONS = "contactPersons";
	private List<ContactPerson> contactPersons = new ArrayList<>();

	public static final String SERIALIZED_NAME_START_DATE = "startDate";
	private LocalDate startDate;

	public static final String SERIALIZED_NAME_END_DATE = "endDate";
	private LocalDate endDate;

	public ContactPersonList contactPersons(List<ContactPerson> contactPersons) {

		this.contactPersons = contactPersons;
		return this;
	}

	public ContactPersonList addContactPersonsItem(ContactPerson contactPersonsItem) {
		this.contactPersons.add(contactPersonsItem);
		return this;
	}

	/**
	 * Get contactPersons
	 * 
	 * @return contactPersons
	 **/

	public List<ContactPerson> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
	}

	public ContactPersonList startDate(LocalDate startDate) {

		this.startDate = startDate;
		return this;
	}

	/**
	 * Start date of contacts for this list.
	 * 
	 * @return startDate
	 **/

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public ContactPersonList endDate(LocalDate endDate) {

		this.endDate = endDate;
		return this;
	}

	/**
	 * End date of contacts for this list.
	 * 
	 * @return endDate
	 **/

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ContactPersonList contactPersonList = (ContactPersonList) o;
		return Objects.equals(this.contactPersons, contactPersonList.contactPersons) &&
				Objects.equals(this.startDate, contactPersonList.startDate) &&
				Objects.equals(this.endDate, contactPersonList.endDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(contactPersons, startDate, endDate);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ContactPersonList {\n");
		sb.append("    contactPersons: ").append(toIndentedString(contactPersons)).append("\n");
		sb.append("    startDate: ").append(toIndentedString(startDate)).append("\n");
		sb.append("    endDate: ").append(toIndentedString(endDate)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}