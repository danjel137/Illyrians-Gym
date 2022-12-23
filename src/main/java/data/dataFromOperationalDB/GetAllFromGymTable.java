package data.dataFromOperationalDB;

import model.operationalDatabase.Gym;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromGymTable {
    private GetAllFromGymTable() {
    }

    public static PCollection<Gym> get(Pipeline pipeline) {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");
       // System.out.println( System.getenv("hostAndDbName"));
        return pipeline.apply(JdbcIO.<Gym>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword("12345600"))
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
                }));
    }
}
