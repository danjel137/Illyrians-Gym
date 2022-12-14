package data.dataFromOperationalDB;

import model.operationalDatabase.Session;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromSessionTable {
    private GetAllFromSessionTable() {
    }

    public static PCollection<Session> get(Pipeline pipeline) {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");

        return pipeline.apply(JdbcIO.<Session>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from session;")
                .withCoder(SerializableCoder.of(Session.class))
                .withRowMapper(new JdbcIO.RowMapper<Session>() {
                    public Session mapRow(ResultSet resultSet) throws Exception {
                        return new Session(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getInt(7),
                                resultSet.getInt(8),
                                resultSet.getString(9)
                        );
                    }
                })
        );
    }
}
