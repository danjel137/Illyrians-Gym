package service.participants.analyses;

import model.analytics.db.ParticipantsStatistics;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateParticipantStatistics {

    private GenerateParticipantStatistics() {

    }

    public static PCollection<ParticipantsStatistics> getTransform(Pipeline pipeline
            , PCollectionView<List<KV<String, Long>>> sessionTypeMinView
            , PCollectionView<List<KV<String, Long>>> sessionTypeMaxView
            , PCollectionView<List<KV<String, Double>>> sessionTypeAverageView
            , PCollectionView<List<KV<String, Double>>> sessionTypeMedianView
            , PCollectionView<List<KV<String, String>>> sessionTypeMostFrequentGenderView
            , PCollectionView<List<KV<String, String>>> sessionTypeNameSurnameOfMostFrequentUserView
            , PCollectionView<List<KV<String, String>>> sessionTypeNameSurnameOfMostFrequentMaleUserView
            , PCollectionView<List<KV<String, String>>> sessionTypeNameSurnameOfMostFrequentFemaleUserView
            , PCollectionView<Map<String, AtomicInteger>> availableMappedIdsView
    ) {

        return pipeline.apply(Create.of(1))
                .apply(ParDo.of(new DoFn<Integer, ParticipantsStatistics>() {
                    final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    @ProcessElement
                    public void processElement(ProcessContext context) {

                        List<KV<String, Long>> sessionTypeMinList = context.sideInput(sessionTypeMinView);
                        List<KV<String, Long>> sessionTypeMaxList = context.sideInput(sessionTypeMaxView);
                        List<KV<String, Double>> sessionTypeAverageList = context.sideInput(sessionTypeAverageView);
                        List<KV<String, Double>> sessionTypeMedianList = context.sideInput(sessionTypeMedianView);
                        List<KV<String, String>> sessionTypeMostFrequentGenderList = context.sideInput(sessionTypeMostFrequentGenderView);
                        List<KV<String, String>> sessionTypeNameSurnameOfMostFrequentUserList = context.sideInput(sessionTypeNameSurnameOfMostFrequentUserView);
                        List<KV<String, String>> sessionTypeNameSurnameOfMostFrequentMaleUserList = context.sideInput(sessionTypeNameSurnameOfMostFrequentMaleUserView);
                        List<KV<String, String>> sessionTypeNameSurnameOfMostFrequentFemaleUserList = context.sideInput(sessionTypeNameSurnameOfMostFrequentFemaleUserView);
                        Map<String, AtomicInteger> availableMappedIds = context.sideInput(availableMappedIdsView);

                        for (KV<String, Long> sessionTypeMinKVPivot : sessionTypeMinList) {

                            ParticipantsStatistics participantsStatistics = new ParticipantsStatistics();
                            Date date = new Date();

                            participantsStatistics.setSessionType(sessionTypeMinKVPivot.getKey());
                            participantsStatistics.setMinNumParticipants(sessionTypeMinKVPivot.getValue());
                            participantsStatistics.setResultDay(formatter.format(date));
                            participantsStatistics.setId(availableMappedIds.get("participantsAnalyseId").getAndIncrement());

                            for (KV<String, Long> sessionTypeMax : sessionTypeMaxList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeMax.getKey())) {
                                    participantsStatistics.setMaxNumParticipants(sessionTypeMax.getValue());
                                }
                            }

                            for (KV<String, Double> sessionTypeAverage : sessionTypeAverageList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeAverage.getKey())) {
                                    participantsStatistics.setAverageNumParticipants(sessionTypeAverage.getValue());
                                }
                            }

                            for (KV<String, Double> sessionTypeMedian : sessionTypeMedianList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeMedian.getKey())) {
                                    participantsStatistics.setMedianNumParticipants(sessionTypeMedian.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeMostFrequentGender : sessionTypeMostFrequentGenderList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeMostFrequentGender.getKey())) {
                                    participantsStatistics.setMostFrequentGender(sessionTypeMostFrequentGender.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeNameSurnameOfMostFrequentUser : sessionTypeNameSurnameOfMostFrequentUserList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeNameSurnameOfMostFrequentUser.getKey())) {
                                    participantsStatistics.setMostFrequentPersonNameSurname(sessionTypeNameSurnameOfMostFrequentUser.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeNameSurnameOfMostFrequentMaleUser : sessionTypeNameSurnameOfMostFrequentMaleUserList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeNameSurnameOfMostFrequentMaleUser.getKey())) {
                                    participantsStatistics.setMostFrequentMaleNameSurname(sessionTypeNameSurnameOfMostFrequentMaleUser.getValue());
                                }
                            }

                            for (KV<String, String> sessionTypeNameSurnameOfMostFrequentFemaleUser : sessionTypeNameSurnameOfMostFrequentFemaleUserList) {
                                if (sessionTypeMinKVPivot.getKey().equals(sessionTypeNameSurnameOfMostFrequentFemaleUser.getKey())) {
                                    participantsStatistics.setMostFrequentMaleNameSurname(sessionTypeNameSurnameOfMostFrequentFemaleUser.getValue());
                                }
                            }
                            context.output(participantsStatistics);
                        }
                    }

                }).withSideInputs(sessionTypeMinView,
                        sessionTypeMaxView,
                        sessionTypeAverageView,
                        sessionTypeMedianView,
                        sessionTypeMostFrequentGenderView,
                        sessionTypeNameSurnameOfMostFrequentUserView,
                        sessionTypeNameSurnameOfMostFrequentMaleUserView,
                        sessionTypeNameSurnameOfMostFrequentFemaleUserView,
                        availableMappedIdsView
                ));


    }

}


