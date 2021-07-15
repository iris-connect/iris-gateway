package iris.demo.contact_diary_app.submission.rpc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

  private String street;
  private String houseNumber;
  private String zipCode;
  private String city;
}
