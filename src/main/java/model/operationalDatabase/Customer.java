package model.operationalDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {
    int customerId;
    String name;
    String surname;
    String birthday;
    String gender;
    String phoneNumber;
    String status;
}
