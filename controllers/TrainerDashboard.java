package controllers;

import models.Assessment;
import models.GymClass;
import models.Member;
import org.joda.time.LocalDate;
import play.Logger;
import play.mvc.Controller;
import utils.Analytics;
import utils.MemberStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TrainerDashboard extends Controller {
  public static void index() {
    List<Member> members = Member.findAll();
    List<GymClass> gymclasses = GymClass.findAll();
    Logger.info("Rendering Trainer Dashboard");
    render("trainerdashboard.html", members, gymclasses);
  }

  public static void trainerAssessment(Long id) {
    Member member = Member.findById(id);
    List<Assessment> assessments = member.assessments;
    MemberStats memberStats = Analytics.generateMemberStats(member);
    Collections.reverse(assessments);
    render("trainerassessment.html", member, assessments, memberStats);
  }

  public static void editComment(Long id, String comment) {
    Logger.info("Comment " + comment);
    Assessment assessment = Assessment.findById(id);
    assessment.comment = comment;
    assessment.save();
    redirect("/trainerdashboard");
  }

  public static void deleteMember(Long id) {
    Member member = Member.findById(id);
    if (member != null) {
      member.delete();
    }
    redirect("/trainerdashboard");
  }

  public static void deleteGymClass(Long id) {
    GymClass gymClass = GymClass.findById(id);
    if (gymClass != null) {
      gymClass.delete();
    }
    Logger.info("Deleting " + gymClass.name);
    redirect("/trainerdashboard");
  }

  public static void gymClassDetails(Long id) {
    GymClass gymclass = GymClass.findById(id);
    render("gymclassdetails.html", gymclass);
  }

  public static void editGymClassDetails(Long id, GymClass gymclass1) {
    GymClass gymclass = GymClass.findById(id);

    gymclass.date = gymclass1.date;
    gymclass.difficulty = gymclass1.difficulty;
    gymclass.duration = gymclass1.duration;
    gymclass.weeks = gymclass1.weeks;
    gymclass.capacity = gymclass1.capacity;
    gymclass.timeSlot = gymclass1.timeSlot;


    gymclass.save();
    Logger.info("Updating gym class " + gymclass.name);
    redirect("/trainerdashboard");
  }

  public static void createGymClass() {
    render("addgymclass.html");
  }

  public static void addGymClass(String name, double duration, String timeSlot, int capacity, int weeks, String difficulty, String date) {
    Logger.info("Creating Gym Class " + name);

    GymClass gymclass = new GymClass(name, duration, timeSlot, capacity, weeks, difficulty, date);
    gymclass.save();
    index();
    redirect("/trainerdashboard");
  }

  //public static void gymclassDates(GymClass gymClass) throws ParseException {
  //String date = gymClass.date;
  //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
  //Date date1 = sdf.parse(date);
  //date1.after(LocalDate) =
}