/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.healthIMIS.iris.client.data_request;

import de.healthIMIS.iris.client.core.Aggregate;
import de.healthIMIS.iris.client.core.Id;
import de.healthIMIS.iris.client.core.SormasRefId;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

/**
 * @author Jens Kutzsche
 */
@Entity
@Table(name = "data_request")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@Getter
@Setter(AccessLevel.PACKAGE)
public class DataRequest extends Aggregate<DataRequest, DataRequest.DataRequestIdentifier> {

	private SormasRefId refId;
	@AttributeOverride(name = "refId", column = @Column(name = "personId"))
	private SormasRefId personId;
	private String irisUserId;
	private String sormasUserId;

	private Instant requestStart;
	private Instant requestEnd;

	private @Lob String requestDetails;

	@Enumerated(EnumType.STRING) @ElementCollection(fetch = FetchType.EAGER) @CollectionTable(
			name = "data_request_feature",
			joinColumns = @JoinColumn(name = "request_id")) @Column(name = "feature", nullable = false)
	private Set<Feature> features;

	@Column(nullable = false) @Enumerated(EnumType.STRING)
	private Status status = Status.Open;

	public DataRequest(SormasRefId refId, SormasRefId personId, Instant requestStart, Instant requestEnd,
			String requestDetails, String irisUserId, String sormasUserId, Set<Feature> features) {

		super();

		this.id = DataRequestIdentifier.of(UUID.randomUUID());
		this.refId = refId;
		this.personId = personId;
		this.requestStart = requestStart;
		this.requestEnd = requestEnd;
		this.requestDetails = requestDetails;
		this.irisUserId = irisUserId;
		this.sormasUserId = sormasUserId;
		this.features = features;
	}

	@Embeddable
	@EqualsAndHashCode
	@RequiredArgsConstructor(staticName = "of")
	@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
	public static class DataRequestIdentifier implements Id, Serializable {

		private static final long serialVersionUID = -8254677010830428881L;

		final UUID requestId;

		/**
		 * for JSON deserialization
		 */
		public static DataRequestIdentifier of(String uuid) {
			return of(UUID.fromString(uuid));
		}

		@Override
		public String toString() {
			return requestId.toString();
		}
	}

	public enum Feature {
		Contacts_Events, Guests
	}

	public enum Status {
		Open
	}
}
