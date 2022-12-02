package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session  implements Serializable {
    int sessionId;
    String title;
    String description;
    String type;
    int difficultyLevel;
    int length;
    String timeDate;
    int starReviews;
    int numSubscribedCustomer;
}
