package data;

import model.CustomerSession;
import model.TrainerSession;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromTrainerSessionTable {
    public PCollection<TrainerSession> get(Pipeline pipeline) {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");

        return pipeline.apply(JdbcIO.<TrainerSession>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from trainer_session;")
                .withCoder(SerializableCoder.of(TrainerSession.class))
                .withRowMapper(new JdbcIO.RowMapper<TrainerSession>() {
                    public TrainerSession mapRow(ResultSet resultSet) throws Exception {
                        return new TrainerSession(
                                resultSet.getInt(1),
                                resultSet.getInt(2)
                        );
                    }
                })
        );
    }
}
