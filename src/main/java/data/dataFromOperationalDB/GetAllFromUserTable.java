package data.dataFromOperationalDB;

import model.operationalDatabase.User;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.sql.ResultSet;

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
        .withQuery("select * from user")
        .withCoder(SerializableCoder.of(User.class))
        .withRowMapper(new JdbcIO.RowMapper<User>() {
          public User mapRow(ResultSet resultSet) throws Exception {
            return new User(
                resultSet.getInt(1),//id
                resultSet.getString(2),//name
                resultSet.getString(3),//surname
                resultSet.getString(4),//email
                resultSet.getString(5),//password
                resultSet.getString(6),//user type
                resultSet.getDate(7),//birthday
                resultSet.getString(8),//gender
                resultSet.getString(9),//phone nr
                resultSet.getDate(10), //time date regist
                resultSet.getString(11),//trainer insta
                resultSet.getString(12),//trainer tit
                resultSet.getString(13),//train descrpt
                resultSet.getInt(14),//gum id
                resultSet.getDate(15));//end date subst
          }
        });
  }
}
