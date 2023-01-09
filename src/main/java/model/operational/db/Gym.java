package model.operational.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gym implements Serializable {
    int gymId;
    String gymName;
    String location;
    String schedule;
    String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gym gym = (Gym) o;
        return gymId == gym.gymId && Objects.equals(gymName, gym.gymName) && Objects.equals(location, gym.location) && Objects.equals(schedule, gym.schedule) && Objects.equals(description, gym.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gymId, gymName, location, schedule, description);
    }
}
