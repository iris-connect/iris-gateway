package de.healthIMIS.iris.dummy_app.submissions;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Extended person data type for a guest who attended a queried event or location in the queried time.
 */

public class Guest extends Person {

	@JsonProperty("attendanceInformation")
	private GuestAttendanceInformation attendanceInformation = null;

	@JsonProperty("identityChecked")
	private Boolean identityChecked = null;

	public Guest attendanceInformation(GuestAttendanceInformation attendanceInformation) {
		this.attendanceInformation = attendanceInformation;
		return this;
	}

	/**
	 * Get attendanceInformation
	 * 
	 * @return attendanceInformation
	 **/
	public GuestAttendanceInformation getAttendanceInformation() {
		return attendanceInformation;
	}

	public void setAttendanceInformation(GuestAttendanceInformation attendanceInformation) {
		this.attendanceInformation = attendanceInformation;
	}

	public Guest identityChecked(Boolean identityChecked) {
		this.identityChecked = identityChecked;
		return this;
	}

	/**
	 * Get identityChecked
	 * 
	 * @return identityChecked
	 **/
	public Boolean isIdentityChecked() {
		return identityChecked;
	}

	public void setIdentityChecked(Boolean identityChecked) {
		this.identityChecked = identityChecked;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Guest guest = (Guest) o;
		return Objects.equals(this.attendanceInformation, guest.attendanceInformation)
				&& Objects.equals(this.identityChecked, guest.identityChecked) && super.equals(o);
	}

	@Override
	public int hashCode() {
		return Objects.hash(attendanceInformation, identityChecked, super.hashCode());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Guest {\n");
		sb.append("    ").append(toIndentedString(super.toString())).append("\n");
		sb.append("    attendanceInformation: ").append(toIndentedString(attendanceInformation)).append("\n");
		sb.append("    identityChecked: ").append(toIndentedString(identityChecked)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}
