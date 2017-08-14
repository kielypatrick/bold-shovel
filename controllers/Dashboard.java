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

import static play.Play.id;

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
    Logger.info( "Rendering class details for " + gymclass.name);
    render("membergymclassdetails.html", gymclass);

  }

  public static void addGymClass(Long id)
  {
    GymClass gymclass = GymClass.findById(id);

    Member member = Accounts.getLoggedInMember();
    member.addGymClass(gymclass, member);
    member.save();
    Logger.info("Enrollin " + member.name + " in " + gymclass.name);
    redirect("/dashboard");
  }

  public static void quitGymClass(Long gymclassid, Long memberid)
  {
    Member member = Member.findById(memberid);
    GymClass gymClass = GymClass.findById(gymclassid);
    member.gymclasses.remove(gymClass);
    member.save();
    redirect("/dashboard");


  }
}
