package data.dataToCsvFilesOfResultsOfSAnalyses;

import model.analyticsDatabase.ParticipantsStatistics;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import service.ParseAsCsv;

import java.util.Objects;

public class WriteToParticipantsStatisticsAnalyseFile {
    private WriteToParticipantsStatisticsAnalyseFile() {
    }

    public static void write(PCollection<ParticipantsStatistics> input,String filename,String header) {
        input.apply(ParDo.of(new DoFn<ParticipantsStatistics, String>() {
                    @ProcessElement
                    public void processElement(ProcessContext context) {
                        context.output(ParseAsCsv.parse(Objects.requireNonNull(context.element())));
                    }
                }))
                .apply(TextIO.write().to(filename)
                        .withSuffix(".csv")
                        .withHeader(header)
                );
    }
}