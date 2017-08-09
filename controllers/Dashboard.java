package controllers;

import models.Assessment;
import models.GymClass;
import models.Member;
import play.Logger;
import play.mvc.Controller;
import utils.Analytics;
import utils.MemberStats;

import java.util.Collections;
import java.util.List;

public class Dashboard extends Controller
{
  public static void index()
  {
    Logger.info("Rendering Dashboard");
    Member member = Accounts.getLoggedInMember();
    List<Assessment> assessments = member.assessments;
    List<GymClass> gymclasses = GymClass.findAll();
    MemberStats memberStats = Analytics.generateMemberStats(member);
    Collections.reverse(assessments);
    render("dashboard.html", member, assessments, memberStats, gymclasses);
  }

  public static void addAssessment(double weight, double chest, double thigh, double upperarm, double waist, double hips)
  {
    Logger.info("Creating Assessment");
    Member member = Accounts.getLoggedInMember();
    Assessment assessment = new Assessment(weight, chest, thigh, upperarm, waist, hips);
    MemberStats memberStats = Analytics.generateMemberStats(member);
    assessment.trend = memberStats.trend;
    member.assessments.add(assessment);
    member.save();
    redirect("/dashboard");
  }

  public static void deleteAssessment(Long memberid, Long assessmentid)
  {
    Member member = Member.findById(memberid);
    Assessment assessment = Assessment.findById(assessmentid);
    member.assessments.remove(assessment);
    member.save();
    assessment.delete();
    redirect("/dashboard");
  }

  public static void gymClassDetails(Long id)
  {
    GymClass gymclass = GymClass.findById(id);
    render("membergymclassdetails.html", gymclass);
  }

  public static void addGymClass()
  {
    render();
  }
}
