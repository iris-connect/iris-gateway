package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

  private String street;
  private String houseNumber;
  private String zipCode;
  private String city;
}
