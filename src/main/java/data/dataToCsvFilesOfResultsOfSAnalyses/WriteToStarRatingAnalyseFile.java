package data.dataToCsvFilesOfResultsOfSAnalyses;

import model.analyticsDatabase.StarRatingStatisticsPerMonth;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;
import service.CSVUtils;

import java.util.Objects;

public class WriteToStarRatingAnalyseFile {
  private WriteToStarRatingAnalyseFile() {
  }

  public static PTransform<PCollection<StarRatingStatisticsPerMonth>, PDone> getTransform(String filename, String header) {
    return new PTransform<PCollection<StarRatingStatisticsPerMonth>, PDone>() {
      @Override
      public PDone expand(PCollection<StarRatingStatisticsPerMonth> input) {
        return input.apply(ParDo.of(new DoFn<StarRatingStatisticsPerMonth, String>() {
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
