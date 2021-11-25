package iris.backend_service.configurations;

/**
 * @author Jens Kutzsche
 */
public class CentralConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 3036327514214351411L;

	CentralConfigurationException(String message) {
		super(message);
	}
}
