import autovalue.shaded.org.checkerframework.checker.nullness.qual.Nullable;
import com.google.api.services.bigquery.model.TableRow;
import data.dataFromOperationalDB.GetAllFromSessionTable;
import data.dataFromOperationalDB.GetAllFromUserSessionTable;
import data.dataFromOperationalDB.GetAllFromUserTable;
import model.analyticsDatabase.SessionStatistics;
import model.operationalDatabase.Session;
//import org.apache.beam.runners.dataflow.DataflowRunner;
//import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import model.operationalDatabase.User;
import model.operationalDatabase.UserSession;
import org.apache.beam.runners.core.construction.renderer.PipelineDotRenderer;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.coders.RowCoder;
import org.apache.beam.sdk.extensions.sql.SqlTransform;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import session.statistics.MinMaxAvgMedianSessionRepeat.UtilityMinMaxAvgMedianRepeat;
import session.statistics.avgGenderRate.KVUserGender;
import session.statistics.avgGenderRate.UtilsRateGender;
import session.statistics.minMaxAvgMedianDifficultyLevel.UtiliyDifficultyLevel;
import session.statistics.most.time.frequent.gender.UtilityTimeFrequent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static session.statistics.avgGenderRate.UtilsRateGender.pipeline;


public class Main {



    public static void main(String[] args) {
//        final DataflowPipelineOptions options = PipelineOptionsFactory.as(DataflowPipelineOptions.class);
//
//        // final Logger LOGGER = LoggerFactory.getLogger(WriteToBq.class);
//        options.setProject("symmetric-hull-368913");
//        options.setRunner(DataflowRunner.class);
//        options.setGcpTempLocation("gs://kotrobt/temp");
//        options.setRegion("europe-west1");
//        options.setJobName("writeSessionStatisticsGymProject");


        PipelineOptions options = PipelineOptionsFactory.create();
        Pipeline pipeline = Pipeline.create(options);

//
//        PCollectionView<List<KV<String, Integer>>> maxDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(0).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> maxDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(1).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> maxDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(2).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> maxDiffLevelPersonalTraining= UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(3).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> maxDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(4).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> maxDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeMaxDifficultyLevel(pipeline).get(5).apply(View.asList());
//
//        PCollectionView<List<KV<String, Integer>>> minDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(0).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> minDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(1).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> minDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(2).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> minDiffLevelPersonalTraining = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(3).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> minDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(4).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> minDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeMinDifficultyLevel(pipeline).get(5).apply(View.asList());
//
//        PCollectionView<List<KV<String, Integer>>> medianDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(0).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> medianDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(1).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> medianDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(2).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> medianDiffLevelPersonalTraining = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(3).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> medianDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(4).apply(View.asList());
//        PCollectionView<List<KV<String, Integer>>> medianDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(5).apply(View.asList());
//
//        PCollectionView<List<KV<String, Double>>> avgDiffLevelYoga = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(0).apply(View.asList());
//        PCollectionView<List<KV<String, Double>>> avgDiffLevelEndurance = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(1).apply(View.asList());
//        PCollectionView<List<KV<String, Double>>> avgDiffLevelFartlek = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(2).apply(View.asList());
//        PCollectionView<List<KV<String, Double>>> avgDiffLevelPersonalTraining = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(3).apply(View.asList());
//        PCollectionView<List<KV<String, Double>>> avgDiffLevelCrossfit = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(4).apply(View.asList());
//        PCollectionView<List<KV<String, Double>>> avgDiffLevelBodyBuilding = UtiliyDifficultyLevel.sessionTypeAvgDifficultyLevel(pipeline).get(5).apply(View.asList());
//
//        UtilsRateGender.setPipeline(pipeline);
//        PCollectionView<List<KV<String, Double>>> mostTimeFrequentFromMale = UtilityTimeFrequent.mostTimeFrequentMale();
//        PCollectionView<List<KV<String, Double>>> mostTimeFrequentFromFemale = UtilityTimeFrequent.mostTimeFrequentFemale();
//
//        UtilityMinMaxAvgMedianRepeat utilityMinMaxAvgMedianRepeat =new UtilityMinMaxAvgMedianRepeat(pipeline);
//        PCollectionView<List<String>> minRepeatSession = utilityMinMaxAvgMedianRepeat.getMinRepeatSession(pipeline).apply(View.asList());
//        PCollectionView<List<String>> maxRepeatSession = utilityMinMaxAvgMedianRepeat.getMaxRepeatSession(pipeline).apply(View.asList());
//        PCollectionView<List<Double>> avgRepeatSession = utilityMinMaxAvgMedianRepeat.getAvgRateNumSession(pipeline).apply(View.asList());

//        TableRow tbrw=new TableRow();
//        pipeline.apply(Create.of(1))
//                .apply(ParDo.of(new DoFn<Integer, TableRow>() {
//            @ProcessElement
//            public void processElement(ProcessContext context) {
//
//                System.out.println( context.sideInput(maxDiffLevelYoga));
//                System.out.println( context.sideInput(maxDiffLevelEndurance));
//                System.out.println( context.sideInput(maxDiffLevelFartlek));
//                System.out.println( context.sideInput(maxDiffLevelPersonalTraining));
//                System.out.println( context.sideInput(maxDiffLevelCrossfit));
//                System.out.println( context.sideInput(maxDiffLevelBodyBuilding));
//
//                System.out.println( context.sideInput(minDiffLevelYoga));
//                System.out.println( context.sideInput(minDiffLevelEndurance));
//                System.out.println( context.sideInput(minDiffLevelFartlek));
//                System.out.println( context.sideInput(minDiffLevelPersonalTraining));
//                System.out.println( context.sideInput(minDiffLevelCrossfit));
//                System.out.println( context.sideInput(minDiffLevelBodyBuilding));
//
//                System.out.println( context.sideInput(medianDiffLevelYoga));
//                System.out.println( context.sideInput(medianDiffLevelEndurance));
//                System.out.println( context.sideInput(medianDiffLevelFartlek));
//                System.out.println( context.sideInput(medianDiffLevelPersonalTraining));
//                System.out.println( context.sideInput(medianDiffLevelCrossfit));
//                System.out.println( context.sideInput(medianDiffLevelBodyBuilding));
//
//                System.out.println( context.sideInput(avgDiffLevelYoga));
//                System.out.println( context.sideInput(avgDiffLevelEndurance));
//                System.out.println( context.sideInput(avgDiffLevelFartlek));
//                System.out.println( context.sideInput(avgDiffLevelPersonalTraining));
//                System.out.println( context.sideInput(avgDiffLevelCrossfit));
//                System.out.println( context.sideInput(avgDiffLevelBodyBuilding));
//
//                System.out.println(context.sideInput(mostTimeFrequentFromMale));
//                System.out.println(context.sideInput(mostTimeFrequentFromFemale));
//
//                System.out.println(context.sideInput(minRepeatSession));
//                System.out.println(context.sideInput(maxRepeatSession));
//                System.out.println(context.sideInput(avgRepeatSession));
//
//                context.output(new TableRow()
//                        .set("maxDiffLevelYoga",maxDiffLevelYoga)
//                        .set("maxDiffLevelEndurance",maxDiffLevelEndurance)
//                        .set("maxDiffLevelFartlek",maxDiffLevelFartlek)
//                        .set("maxDiffLevelPersonalTraining",maxDiffLevelPersonalTraining)
//                        .set("maxDiffLevelCrossfit",maxDiffLevelCrossfit)
//                        .set("maxDiffLevelBodyBuilding",maxDiffLevelBodyBuilding)
//                        .set("minDiffLevelYoga",minDiffLevelYoga)
//                        .set("minDiffLevelEndurance",minDiffLevelEndurance)
//                        .set("minDiffLevelFartlek",minDiffLevelFartlek)
//                        .set("minDiffLevelPersonalTraining",minDiffLevelPersonalTraining)
//                        .set("minDiffLevelCrossfit",minDiffLevelCrossfit)
//                        .set("minDiffLevelBodyBuilding",minDiffLevelBodyBuilding)
//                        .set("medianDiffLevelYoga",medianDiffLevelYoga)
//                        .set("medianDiffLevelEndurance",medianDiffLevelEndurance)
//                        .set("medianDiffLevelFartlek",medianDiffLevelFartlek)
//                        .set("medianDiffLevelPersonalTraining",medianDiffLevelPersonalTraining)
//                        .set("medianDiffLevelCrossfit",medianDiffLevelCrossfit)
//                        .set("medianDiffLevelBodyBuilding",medianDiffLevelBodyBuilding)
//                        .set("avgDiffLevelYoga",avgDiffLevelYoga)
//                        .set("avgDiffLevelEndurance",avgDiffLevelEndurance)
//                        .set("avgDiffLevelFartlek",avgDiffLevelFartlek)
//                        .set("avgDiffLevelPersonalTraining",avgDiffLevelPersonalTraining)
//                        .set("avgDiffLevelCrossfit",avgDiffLevelCrossfit)
//                        .set("avgDiffLevelBodyBuilding",avgDiffLevelBodyBuilding)
//                        .set("mostTimeFrequentFromMale",mostTimeFrequentFromMale)
//                        .set("mostTimeFrequentFromFemale",mostTimeFrequentFromFemale)
//                        .set("minRepeatSession",minRepeatSession)
//                        .set("maxRepeatSession",maxRepeatSession)
//                        .set("avgRepeatSession",avgRepeatSession));
//
//            }
//        }).withSideInputs(maxDiffLevelYoga,maxDiffLevelEndurance,maxDiffLevelFartlek,maxDiffLevelPersonalTraining,maxDiffLevelCrossfit,maxDiffLevelBodyBuilding,
//                minDiffLevelYoga,minDiffLevelEndurance,minDiffLevelFartlek,minDiffLevelPersonalTraining,minDiffLevelCrossfit,minDiffLevelBodyBuilding,
//                medianDiffLevelYoga,medianDiffLevelEndurance,medianDiffLevelFartlek,medianDiffLevelPersonalTraining,medianDiffLevelCrossfit,medianDiffLevelBodyBuilding,
//                avgDiffLevelYoga,avgDiffLevelEndurance,avgDiffLevelFartlek,avgDiffLevelPersonalTraining,avgDiffLevelCrossfit,avgDiffLevelBodyBuilding,
//                mostTimeFrequentFromMale,mostTimeFrequentFromFemale,minRepeatSession,maxRepeatSession,avgRepeatSession
//        ));
//                .apply(BigQueryIO.writeTableRows().to("symmetric-hull-368913.session.session_statistics")
//                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
//                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND).withCustomGcsTempLocation(new ValueProvider<String>() {
//                            @Override
//                            public String get() {
//                                return "gs://kotrobt/tempbq";
//                            }
//
//                            @Override
//                            public boolean isAccessible() {
//                                return true;
//                            }
//                        }));


//        UtilsRateGender.setPipeline(pipeline);
//        UtilsRateGender.getBiggestRateGenderAvgOfAllTypeSession();

//        UtilityMinMaxAvgMedianRepeat utilityMinMaxAvgMedianRepeat1=new UtilityMinMaxAvgMedianRepeat(pipeline);
//        utilityMinMaxAvgMedianRepeat1.getAvgRateNumSession(pipeline);

//          UtilityTimeFrequent.mostTimeFrequentMale();

     //   UtiliyDifficultyLevel.sessionTypeMedianDifficultyLevel(pipeline).get(0);
//        Schema type =

//
//       // Row row1 =Row.withSchema(type).ad
//        PCollection<Row> bla = pipeline.apply(GetAllFromSessionTable.get()).apply(ParDo.of(new DoFn<Session, Row>() {
//            @ProcessElement
//            public void aVoid(ProcessContext c) {
//                System.out.println(c.element());

//                Row row1 = Row.withSchema(type).addValues(c.element()).build();
//                c.output(row1);
//            }
//        }));
//        output.apply(MapElements.via(new SimpleFunction<Row, Row>() {
//            @Override
//            public  Row apply(Row input){
//                System.out.println(input.getValues());
//                return  input;
//            }
//        }));
//
//        resultSet.getInt(1),//id
//                resultSet.getString(2),//name
//                resultSet.getString(3),//surname
//                resultSet.getString(4),//email
//                resultSet.getString(5),//password
//                resultSet.getString(6),//user type
//                resultSet.getDate(7),//birthday
//                resultSet.getString(8),//gender
//                resultSet.getString(9),//phone nr
//                resultSet.getDate(10), //time date regist
//                resultSet.getString(11),//trainer insta
//                resultSet.getString(12),//trainer tit
//                resultSet.getString(13),//train descrpt
//                resultSet.getInt(14),//gum id
//                resultSet.getDate(15));//end date subst

        Schema schemaSession= Schema.builder().addInt32Field("session_id").addStringField("description").addInt32Field("diff_level")
                        .addInt32Field("duration_mins").addInt32Field("max_participants").addStringField("start_time")
                        .addStringField("session_type").addStringField("day_week").build();

        PCollection<Row> rowSession = pipeline.apply(GetAllFromSessionTable.get()).apply(ParDo.of(new DoFn<Session, Row>() {
            @ProcessElement
            public void ap(ProcessContext c) {
                Row row = Row.withSchema(schemaSession).addValues(c.element().getSessionId(),c.element().getDescription(),c.element().getDifficultyLevel(),c.element().getDurationMins()
                        ,c.element().getMaxNumParticipants(),c.element().getStartTime(),c.element().getSessionType(),c.element().getDayWeek()).build();
                //System.out.println(row);
                c.output(row);

            }
        }))
        .setCoder(RowCoder.of(schemaSession));

        //rowSession.apply(SqlTransform.query("select * from PCOLLECTION"));

        Schema sessionUserSchema =Schema.builder().addInt32Field("userId").addInt32Field("session_id")
                .addDoubleField("rate").addStringField("dateRegisteredSession").build();
        PCollection<Row> rowUserSession = pipeline.apply(GetAllFromUserSessionTable.get()).apply(ParDo.of(new DoFn<UserSession,Row>() {
            @ProcessElement
            public void apply(ProcessContext c) {
                UserSession userSession=c.element();
                if(userSession.getDateRegisteredSession()!=null ) {

                    Row row = Row.withSchema(sessionUserSchema).addValues(c.element().getUserId(), c.element().getSessionId(), c.element().getRate(), c.element().getDateRegisteredSession())
                            .build();
                    c.output(row);
                }

                }

        })).setCoder(RowCoder.of(sessionUserSchema));

        //rowUserSession.apply(SqlTransform.query("select * from PCOLLECTION"));

//
        Schema UserSchema =Schema.builder().addInt32Field("user_id").addStringField("firstName").addStringField("lastName")
                .addStringField("email").addStringField("password").addStringField("userType").addDateTimeField("birthday")
                .addStringField("gender").addStringField("phoneNumber").addDateTimeField("timeDateRegistered")
                .addStringField("trainerInstagramAccount").addStringField("trainerTitle").addStringField("trainerDescription")
                .addInt32Field("gym_id").addDateTimeField("endTimeSubscription").build();

        PCollection<Row> rowUser = pipeline.apply(GetAllFromUserTable.get()).apply(ParDo.of(new DoFn<User, Row>() {
            @ProcessElement
            public void apply(ProcessContext c) {
                User user=c.element();
                if(user.getUserType()!=null) {
                    Row row = Row.withSchema(UserSchema).addValues(c.element().getUserId(), c.element().getFirstName(), c.element().getLastName(),
                            c.element().getEmail(), c.element().getPassword(), c.element().getUserType(), c.element().getBirthday(),
                            c.element().getGender(), c.element().getPhoneNumber(), c.element().getTimeDateRegistered(), c.element().getTrainerInstagramAccount(),
                            c.element().getTrainerTitle(), c.element().getTrainerDescription(), c.element().getGymId(), c.element().getEndTimeSubscription()).build();
                    c.output(row);

                }
            }

        })).setCoder(RowCoder.of(sessionUserSchema));

        PCollection<Row> joined = PCollectionTuple.of(new TupleTag<>("rowSession"), rowSession)
                .and(new TupleTag<>("rowUserSession"), rowUserSession)
                .and(new TupleTag<>("rowUser"),rowUser)
                .apply("Join PCollections",
                        SqlTransform.query("select se.start_time ,sum(user_id) su\n" +
                                "from user_session us\n" +
                                "join session se\n" +
                                "on se.session_id=us.session_id\n" +
                                "join \"user\" use \n" +
                                "on us.user_id=use.user_id\n" +
                                "where use.gender='M'\n" +
                                "group by se.start_time\n" +
                                "order by su desc\n" +
                                "limit 1"));




                     //   rowUser.apply(SqlTransform.query("select * from PCOLLECTION"));

        joined.apply(MapElements.via(new SimpleFunction<Row, Row>() {
            @Override
            public  Row apply(Row input){
                System.out.println(input.getValues());
                return  input;
            }
        })).setCoder(RowCoder.of(schemaSession));

        pipeline.run();

    }

}
