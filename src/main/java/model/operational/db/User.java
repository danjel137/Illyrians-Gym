package model.operational.db;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class User implements Serializable {
    int userId;
    String firstName;
    String lastName;
    String email;
    String password;
    String userType;
    String birthday;
    String gender;
    String phoneNumber;
    String timeDateRegistered;
    String trainerInstagramAccount;
    String trainerTitle;
    String trainerDescription;
    int gymId;
    String endTimeSubscription;
}
