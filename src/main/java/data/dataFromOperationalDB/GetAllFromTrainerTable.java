package data.dataFromOperationalDB;

import model.operationalDatabase.Trainer;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromTrainerTable {
    private GetAllFromTrainerTable() {
    }

    public static PCollection<Trainer> get(Pipeline pipeline) {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");

        return pipeline.apply(JdbcIO.<Trainer>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from trainer;")
                .withCoder(SerializableCoder.of(Trainer.class))
                .withRowMapper(new JdbcIO.RowMapper<Trainer>() {
                    public Trainer mapRow(ResultSet resultSet) throws Exception {
                        return new Trainer(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getInt(6)
                        );
                    }
                })
        );
    }
}
