package model.operationalDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trainer  implements Serializable {
    int trainerId;
    String name;
    String title;
    String socialMediaAccount;
    String biography;
    int gymId;
}
