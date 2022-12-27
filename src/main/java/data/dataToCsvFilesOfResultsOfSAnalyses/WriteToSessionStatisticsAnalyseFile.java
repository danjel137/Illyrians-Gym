package data.dataToCsvFilesOfResultsOfSAnalyses;

import model.analyticsDatabase.SessionStatistics;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import service.CSVUtils;

import java.io.Serializable;
import java.util.Objects;

public class WriteToSessionStatisticsAnalyseFile implements Serializable {
  private WriteToSessionStatisticsAnalyseFile() {
  }

  public static PTransform<PCollection<SessionStatistics>, PDone> getTransform(String filename, String header) {
    return new PTransform<PCollection<SessionStatistics>, PDone>() {
      @Override
      public PDone expand(PCollection<SessionStatistics> input) {
        return input.apply(ParDo.of(new DoFn<SessionStatistics, String>() {
              @ProcessElement
              public void processElement(ProcessContext context) {
                context.output(CSVUtils.toCsvLine(Objects.requireNonNull(context.element())));
              }
            }))
            .apply(TextIO.write().to(filename)
                .withSuffix(".csv")
                .withHeader(header)
            );
      }
    };
  }
}
