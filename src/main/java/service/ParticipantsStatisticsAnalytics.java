package service;

import model.analyticsDatabase.ParticipantsStatistics;
import model.operationalDatabase.Session;
import model.operationalDatabase.User;
import model.operationalDatabase.UserSession;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.values.PCollection;

public class ParticipantsStatisticsAnalytics {
  private ParticipantsStatisticsAnalytics() {
  }

  public static PCollection<ParticipantsStatistics> calculate(
      Pipeline pipeline
      , PCollection<Session> session
      , PCollection<User> users
      , PCollection<UserSession> usersSessions) {


    return null;
  }
}
