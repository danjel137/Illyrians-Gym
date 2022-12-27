package session.statistics.avgGenderRate;

import org.apache.beam.sdk.values.KV;

import java.io.Serializable;
import java.util.Comparator;

public class KVComparator implements Comparator<KV<String, Double>>, Serializable {
  @Override
  public int compare(KV<String, Double> o1, KV<String, Double> o2) {
    return o1.getValue().compareTo(o2.getValue());
  }
}
