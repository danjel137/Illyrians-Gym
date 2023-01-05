package data.dataFromOperationalDB;

import model.operationalDatabase.Session;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.sql.ResultSet;

public class GetAllFromSessionTable implements Serializable {
    private GetAllFromSessionTable() {
    }

    public static PTransform<PBegin, PCollection<Session>> getTransform() {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");
        return JdbcIO.<Session>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from session;")
                .withCoder(SerializableCoder.of(Session.class))
                .withRowMapper(new JdbcIO.RowMapper<Session>() {
                    public Session mapRow(ResultSet resultSet) throws Exception {
                        return new Session(
                                resultSet.getInt(1),//id
                                resultSet.getString(2),//description
                                resultSet.getInt(3),//diff level
                                resultSet.getInt(4),//duration mins
                                resultSet.getInt(5),//max participans
                                resultSet.getString(6),//start time
                                resultSet.getString(7),//session type
                                resultSet.getString(8)//day week
                        );
                    }
                });
    }

}
