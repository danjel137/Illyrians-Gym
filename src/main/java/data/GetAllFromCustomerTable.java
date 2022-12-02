package data;

import model.Customer;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromCustomerTable extends DoFn<PBegin, PCollection<Customer>> {
    public PCollection<Customer> get(Pipeline pipeline) {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");

        return pipeline.apply(JdbcIO.<Customer>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from customer;")
                .withCoder(SerializableCoder.of(Customer.class))
                .withRowMapper(new JdbcIO.RowMapper<Customer>() {
                    public Customer mapRow(ResultSet resultSet) throws Exception {
                        return new Customer(resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                resultSet.getString(7)
                        );
                    }
                })
        );
    }
}
