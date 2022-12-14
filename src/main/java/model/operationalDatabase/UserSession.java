package model.operationalDatabase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSession implements Serializable {
    int userId;
    int sessionId;
    double rate;
    String dateRegisteredSession;
}
