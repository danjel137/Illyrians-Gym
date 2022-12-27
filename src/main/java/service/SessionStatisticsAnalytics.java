package service;

import model.analyticsDatabase.SessionStatistics;
import model.operationalDatabase.Session;
import model.operationalDatabase.User;
import model.operationalDatabase.UserSession;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.values.PCollection;

public class SessionStatisticsAnalytics {
  private SessionStatisticsAnalytics() {
  }

  public static PCollection<SessionStatistics> calculate(
      Pipeline pipeline, PCollection<Session> sessions,
      PCollection<User> users,
      PCollection<UserSession> usersSessions) {

    return null;
  }
}
