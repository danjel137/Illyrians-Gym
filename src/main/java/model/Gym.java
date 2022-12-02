package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gym  implements Serializable {
   int gymId;
   String gymName;
   String location;
   String description;
   String schedule;
}
