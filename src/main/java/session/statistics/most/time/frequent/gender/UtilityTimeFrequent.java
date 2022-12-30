package session.statistics.most.time.frequent.gender;

import data.dataFromOperationalDB.GetAllFromSessionTable;
import org.apache.beam.sdk.extensions.joinlibrary.Join;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import session.statistics.avgGenderRate.KVComparator;
import session.statistics.avgGenderRate.UtilsRateGender;

import java.io.Serializable;

import static session.statistics.avgGenderRate.UtilsRateGender.pipeline;


public class UtilityTimeFrequent implements Serializable {



    public static PCollection <KV<String, String>> kVTimeStartSessionID(){
      return    GetAllFromSessionTable.get(pipeline)
                .apply("KV sessionID started time", ParDo.of(new ExtractSessionIdStartedTime()));
    }

    public static PCollection <KV<String, String>> kVSessionIdUserIdSpecificGender(){
        return    UtilsRateGender.getUserIdSessionIdOnlyGenderSpecificFromUserSession()
                .apply(ParDo.of(new SessionIdUserIDSpecificGender()));
    }

    public static PCollection<KV<String, String>> joinedSessionTimeUserId(){

         return       Join.innerJoin(
                                kVTimeStartSessionID(), kVSessionIdUserIdSpecificGender())
                        .apply("session time ,user id for each session",ParDo.of(new DoFn<KV<String, KV<String, String>>, KV<String, String>>() {
                            @ProcessElement
                            public void aVoid(ProcessContext c){
//                                System.out.println(c.element().getKey()+": "+c.element().getValue().getKey()+": "+
//                                        c.element().getValue().getValue());
                                c.output(KV.of(c.element().getValue().getKey(), String.valueOf(c.element().getValue().getValue())));//session time ,user id
                                //session id ,session time ,user id
                            }
                        }));
    }
    public static PCollection<KV<String, Double>> maxNumSessionTime(){
     return    joinedSessionTimeUserId()
                .apply(GroupByKey.create())
                .apply(ParDo.of(new NumSessionId()));
    }

    public static PCollection<KV<String, Double>> mostTimeFrequent(){
      return   maxNumSessionTime().apply("most time frequent", Combine.globally(Max.of(new KVComparator())))
                .apply(ParDo.of(new DoFn<KV<String, Double>, KV<String,Double>>() {
                    @ProcessElement
                    public void aVoid(ProcessContext c){
                        System.out.println(c.element());
                        c.output(c.element());
                    }
                }));
    }
}
