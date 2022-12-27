package model.operationalDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    int userId;
    String firstName;
    String lastName;
    String email;
    String password;
    String userType;
    Date birthday;
    String gender;
    String phoneNumber;
    Date timeDateRegistered;
    String trainerInstagramAccount;
    String trainerTitle;
    String trainerDescription;
    int gymId;
    Date endTimeSubscription;
}
