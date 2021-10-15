package iris.backend_service.messages;

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
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Message {

	@EmbeddedId
	private final MessageIdentifier id = MessageIdentifier.of(UUID.randomUUID());

	private String title;
	private String text;
	private String client;
	private String sourceApp;
	private String appVersion;
	private String sender;

	@Column(nullable = false) @Enumerated(EnumType.STRING)
	private MessageType messageType;

	@Embeddable
	@EqualsAndHashCode
	@AllArgsConstructor(staticName = "of")
	@NoArgsConstructor
	public static class MessageIdentifier implements Serializable {

		private static final long serialVersionUID = -8326230397174984557L;

		private UUID id;
	}

	public enum MessageType {
		TICKET, MESSAGE, FEEDBACK
	}
}
