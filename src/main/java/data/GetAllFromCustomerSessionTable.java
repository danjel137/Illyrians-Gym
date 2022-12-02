package data;

import model.CustomerSession;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromCustomerSessionTable extends DoFn<PBegin,PCollection<CustomerSession>> {

    public PCollection<CustomerSession> get(Pipeline pipeline) {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");

        return pipeline.apply(JdbcIO.<CustomerSession>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from customer_session;")
                .withCoder(SerializableCoder.of(CustomerSession.class))
                .withRowMapper(new JdbcIO.RowMapper<CustomerSession>() {
                    public CustomerSession mapRow(ResultSet resultSet) throws Exception {
                        return new CustomerSession(
                                resultSet.getInt(1),
                                resultSet.getInt(2)
                        );
                    }
                })
        );
    }
}
