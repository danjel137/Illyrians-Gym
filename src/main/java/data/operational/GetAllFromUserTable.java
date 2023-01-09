package data.operational;

import model.operational.db.User;
import model.utilities.UserCoder;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public class GetAllFromUserTable implements Serializable {
    private GetAllFromUserTable() {
    }

    public static PTransform<PBegin, PCollection<User>> getTransform() {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");
        return JdbcIO.<User>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from \"user\"")
                .withCoder(UserCoder.of())
                .withRowMapper((JdbcIO.RowMapper<User>) resultSet -> {
                    return new User(
                            resultSet.getInt(1),//id
                            resultSet.getString(2),//name
                            resultSet.getString(3),//surname
                            resultSet.getString(4),//email
                            resultSet.getString(5),//password
                            resultSet.getString(6),//user type
                            resultSet.getString(7),//birthday
                            resultSet.getString(8),//gender
                            resultSet.getString(9),//phone nr
                            resultSet.getString(10), //time date registered acc
                            resultSet.getString(11),//trainer instagram
                            resultSet.getString(12),//trainer tit
                            resultSet.getString(13),//train desc
                            resultSet.getInt(14),//gym id
                            resultSet.getString(15));//end date subst
                });
    }
}
