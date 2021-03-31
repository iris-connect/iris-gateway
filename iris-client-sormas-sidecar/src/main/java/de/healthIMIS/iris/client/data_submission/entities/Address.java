/*
 * IRIS-Gateway API
 * ### Encryption of the data to be transmitted (contact data) In order to be not limited in the amount of data, a hybrid encryption with symmetric encryption of the data and asymmetric encryption of the symmetric key is used for the encryption of the contact data.    1. The apps and applications get the public key of the health department as a 4096-bit RSA key from the IRIS+ server. This key is base64-encoded in the Private Enhanced Mail (PEM) format.   2. The app generates a 256-bit AES key.   3. With this key the data is encrypted (algorithm: AES).   4. The AES key must be encrypted with the public RSA key of the health department. (algorithm: RSA with Optimal Asymmetric Encryption Padding (OAEP))   5. The encrypted AES key and the encrypted content must be transmitted base64 encoded.    #### Schematic sequence    ```   pubKeyEncryption = publicKeyFromPem(givenPublicKey);   contentKey = generateAESKey();    encrypted = contentKey.encrypt(content);   keyEncrypted = pubKeyEncryption.encrypt(contentKey, \"RSA/NONE/OAEPWithSHA3-256AndMGF1Padding\");    dataToTransport = base64Encode(encrypted);   keyToTransport = base64Encode(keyEncrypted);   ``` 
 *
 * The version of the OpenAPI document: 0.2.0
 * Contact: jens.kutzsche@gebea.de
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package de.healthIMIS.iris.client.data_submission.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class Address {

	private String street;
	private String houseNumber;
	private String zipCode;
	private String city;
}
