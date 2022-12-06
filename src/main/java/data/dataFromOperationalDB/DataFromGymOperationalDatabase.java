package data.dataFromOperationalDB;

import model.operationalDatabase.*;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

import java.sql.ResultSet;

public class DataFromGymOperationalDatabase<T> {
//    public PCollection<T> executeQuery(Pipeline pipeline, T objectOfType, String sql) {
//
//        String postgresDriver = "org.postgresql.Driver";
//        String hostname = "jdbc:postgresql://localhost:5432/postgres";
//
//
//        return pipeline.apply(JdbcIO.<T>read()
//                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
//                                postgresDriver, hostname)
//                        .withUsername("postgres")
//                        .withPassword(System.getenv("postgresPASSWORD"))
//
//                )
//
//                .withQuery(sql)
//                .withRowMapper(new JdbcIO.RowMapper<T>() {
//                    public T mapRow(ResultSet resultSet) throws Exception {
//                        T result = null;
//                        if (objectOfType instanceof Customer) {
//                            result = (T) new Customer(resultSet.getInt(1),
//                                    resultSet.getString(2),
//                                    resultSet.getString(3),
//                                    resultSet.getString(4),
//                                    resultSet.getString(5),
//                                    resultSet.getString(6),
//                                    resultSet.getString(7));
//                        } else if (objectOfType instanceof Session) {
//                            result = (T) new Session(
//                                    resultSet.getInt(1),
//                                    resultSet.getString(2),
//                                    resultSet.getString(3),
//                                    resultSet.getString(4),
//                                    resultSet.getInt(5),
//                                    resultSet.getInt(6),
//                                    resultSet.getString(7),
//                                    resultSet.getInt(8),
//                                    resultSet.getInt(9)
//                            );
//                        } else if (objectOfType instanceof Trainer) {
//                            result = (T) new Trainer(
//                                    resultSet.getInt(1),
//                                    resultSet.getString(2),
//                                    resultSet.getString(3),
//                                    resultSet.getString(4),
//                                    resultSet.getString(5),
//                                    resultSet.getInt(6)
//                            );
//                        } else if (objectOfType instanceof Gym) {
//                            result = (T) new Gym(resultSet.getInt(1),
//                                    resultSet.getString(2),
//                                    resultSet.getString(3),
//                                    resultSet.getString(4),
//                                    resultSet.getString(5));
//                        } else if (objectOfType instanceof TrainerSession) {
//                            result = (T) new TrainerSession(
//                                    resultSet.getInt(1),
//                                    resultSet.getInt(2)
//                            );
//                        } else {
//                            result = (T) new CustomerSession(
//                                    resultSet.getInt(1),
//                                    resultSet.getInt(2)
//                            );
//                        }
//                        return result;
//
//                    }
//                })
//        );
//    }
}
