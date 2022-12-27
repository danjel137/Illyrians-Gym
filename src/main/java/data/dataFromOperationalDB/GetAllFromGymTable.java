package data.dataFromOperationalDB;

import model.operationalDatabase.Gym;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.sql.ResultSet;

public class GetAllFromGymTable implements Serializable {
    private GetAllFromGymTable() {
    }

    public static PTransform<PBegin, PCollection<Gym>> getTransform() {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");
        return JdbcIO.<Gym>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from gym")
                .withCoder(SerializableCoder.of(Gym.class))
                .withRowMapper(new JdbcIO.RowMapper<Gym>() {
                    public Gym mapRow(ResultSet resultSet) throws Exception {
                        return new Gym(resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5));

                    }
                });
    }

}
