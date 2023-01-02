package data.dataFromOperationalDB;


import model.operationalDatabase.UserSession;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class GetAllFromUserSessionTable implements JDBCInputFactoryUserSessionTable{
    //private constructor makes no longer possible to create an instance from GetAllFromCustomerSessionTable
    private GetAllFromUserSessionTable() {
    }

    public static PTransform<PBegin, PCollection<UserSession>> get() {
        String postgresDriver = "org.postgresql.Driver";
        String hostname = "jdbc:postgresql://" + System.getenv("hostAndDbName");

        return (JdbcIO.<UserSession>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
                                postgresDriver, hostname)
                        .withUsername("postgres")
                        .withPassword(System.getenv("postgresPASSWORD")))
                .withQuery("select * from user_session;")
                .withCoder(SerializableCoder.of(UserSession.class))
                .withRowMapper(new JdbcIO.RowMapper<UserSession>() {
                    public UserSession mapRow(ResultSet resultSet) throws Exception {
                        return new UserSession(
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getDouble(3),
                                resultSet.getString(4)
                        );
                    }
                })
        );
    }
}
