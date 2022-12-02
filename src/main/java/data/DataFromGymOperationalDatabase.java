package data;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.List;

public class DataFromGymOperationalDatabase<T extends Class> {

//    public PCollection<Object> executeQuery(Pipeline pipeline, T type, List<String> fieldNames, String sql) {
//        String postgresDriver = "org.postgresql.Driver";
//        String hostname = "jdbc:postgresql://localhost:5432/postgres";
//        PCollection<T> result = pipeline.apply(JdbcIO.<T>read()
//                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration.create(
//                                postgresDriver, hostname)
//                        .withUsername("postgres")
//                        .withPassword(System.getenv("postgresPASSWORD")))
//                .withQuery(sql)
//                .withCoder(SerializableCoder.of(type))
//                .withRowMapper(new JdbcIO.RowMapper<T>() {
//                    public T mapRow(ResultSet resultSet) throws Exception {
//
//                        Constructor[] constructors = type.getDeclaredConstructors();
//                        Constructor noParamConstruct = null;
//
//                        for (int i = 0; i <= constructors.length; i++) {
//                            if (constructors[i].getParameterCount() == 0) {
//                                noParamConstruct = constructors[i];
//                                break;
//                            }
//                        }
//
//                        assert noParamConstruct != null;
//
//                        T resultObj = (T) noParamConstruct.newInstance();
//                        return resultObj;
//                    }
//                })
//        );
//        result.apply(MapElements.via(new SimpleFunction<Person, Void>() {
//            @Override
//            public Void apply(Person person) {
//                System.out.println(person.getId() + " " + person.getSurname() + " " + person.getAge());
//                return null;
//            }
//        }));
//
//
//        PCollection<Object> collectionResult = null;
//        return collectionResult;
//    }
}
