package service.utilities;

import model.operationalDatabase.User;
import model.operationalDatabase.UserType;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.util.Objects;

public class FilterUserByType implements Serializable {
  private FilterUserByType() {
  }

  public static PTransform<PCollection<User>,PCollection<User>> getTransform(UserType type) {
    return new PTransform<PCollection<User>, PCollection<User>>() {
      @Override
      public PCollection<User> expand(PCollection<User> input) {
        return input.apply(Filter.by(u -> type.name() == u.getUserType()));
      }
    };
  }

}
