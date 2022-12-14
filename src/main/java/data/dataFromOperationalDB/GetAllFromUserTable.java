package data.dataFromOperationalDB;

import model.operationalDatabase.User;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromUserTable {
    private GetAllFromUserTable() {
    }

    public static PCollection<User> get(Pipeline pipeline) {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");

        return pipeline.apply(JdbcIO.<User>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from user;")
                .withCoder(SerializableCoder.of(User.class))
                .withRowMapper(new JdbcIO.RowMapper<User>() {
                    public User mapRow(ResultSet resultSet) throws Exception {
                        return new User(resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getDate(7),
                                resultSet.getString(8),
                                resultSet.getString(9),
                                resultSet.getDate(10),
                                resultSet.getDate(11),
                                resultSet.getString(12),
                                resultSet.getString(13),
                                resultSet.getString(14),
                                resultSet.getInt(15));
                    }
                })
        );
    }
}
