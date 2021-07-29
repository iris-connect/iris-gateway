package iris.backend_service.alerts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * @author Jens Kutzsche
 */
@Entity
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Alert {

	@EmbeddedId
	private final AlertIdentifier id = AlertIdentifier.of(UUID.randomUUID());

	private String title;
	private String text;
	private String client;
	private String sourceApp;
	private String appVersion;

	@Column(nullable = false) @Enumerated(EnumType.STRING)
	private AlertType alertType;

	@Embeddable
	@EqualsAndHashCode
	@AllArgsConstructor(staticName = "of")
	@NoArgsConstructor
	public static class AlertIdentifier implements Serializable {

		private static final long serialVersionUID = -8326230397174984557L;

		private UUID id;
	}

	public enum AlertType {
		TICKET, MESSAGE
	}
}
