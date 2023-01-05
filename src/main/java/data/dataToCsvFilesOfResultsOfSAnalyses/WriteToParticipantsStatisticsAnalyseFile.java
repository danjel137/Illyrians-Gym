package data.dataToCsvFilesOfResultsOfSAnalyses;

import model.analyticsDatabase.ParticipantsStatistics;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import service.utilities.CSVUtils;

import java.io.Serializable;
import java.util.Objects;

public class WriteToParticipantsStatisticsAnalyseFile implements Serializable {
  private WriteToParticipantsStatisticsAnalyseFile() {
  }

  public static PTransform<PCollection<ParticipantsStatistics>, PDone> getTransform(String filename, String header) {
    return new PTransform<PCollection<ParticipantsStatistics>, PDone>() {
      @Override
      public PDone expand(PCollection<ParticipantsStatistics> input) {
        return input.apply(ParDo.of(new DoFn<ParticipantsStatistics, String>() {
          @ProcessElement
          public void processElement(ProcessContext context) {
            context.output(CSVUtils.toCsvLine(Objects.requireNonNull(context.element())));
          }
        })).setCoder(StringUtf8Coder.of()).apply(TextIO.write().to(filename)
            .withSuffix(".csv")
            .withHeader(header));
      }
    };
  }

}