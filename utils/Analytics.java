package utils;

import models.Assessment;
import models.Member;
import play.Logger;

import java.util.List;

public class Analytics
{
  public static MemberStats generateMemberStats(Member member)
  {
    MemberStats stats = new MemberStats();

    double weight = member.startingweight;
    List<Assessment> assessments = member.assessments;
    if (assessments.size() > 0) {
      Assessment assessment = assessments.get(assessments.size() - 1);
      weight = assessment.weight;
    }
    stats.bmi = calculateBMI(member, weight);
    stats.bmiCategory = determineBMICategory(stats.bmi);
    stats.isIdealBodyweight = isIdealBodyWeight(member, weight);
    stats.trend = true;
    if (assessments.size()>1) {
      stats.trend = assessments.get(assessments.size() - 2).weight > assessments.get(assessments.size() - 1).weight;
    }
    return stats;
  }

  public static double calculateBMI(Member member, double weight)
  {
    if (member.height <= 0)
      return 0;
    else
      return Conversion.round((weight / (member.height * member.height)), 2);
  }

  public static String determineBMICategory(double bmiValue)
  {
    for (BMI bmi : BMI.values()) {
      if (bmi.bmiCategory(bmiValue)) {
        return bmi.toString();
      }
    }
    return "No category available.";
  }

  public static boolean isIdealBodyWeight(Member member, double weight)
  {
    double fiveFeet = 60.0;
    double idealBodyWeight;

    double inches = Conversion.convertMetresToInches(member.height, 2);

    if (inches <= fiveFeet) {
      if (member.gender.equals("M")) {
        idealBodyWeight = 50;
      } else {
        idealBodyWeight = 45.5;
      }
    } else {
      if (member.gender.equals("M")) {
        idealBodyWeight = 50 + ((inches - fiveFeet) * 2.3);
      } else {
        idealBodyWeight = 45.5 + ((inches - fiveFeet) * 2.3);
      }
    }

    Logger.info("Ideal Weight " + idealBodyWeight);
    return ((idealBodyWeight <= (weight + 2.0))
        && (idealBodyWeight >= (weight - 2.0))
    );
  }
}
