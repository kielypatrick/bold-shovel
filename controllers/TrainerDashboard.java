package controllers;

import models.*;
import org.joda.time.LocalDate;
import play.Logger;
import play.mvc.Controller;
import sun.rmi.runtime.Log;
import utils.Analytics;
import utils.MemberStats;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

public class TrainerDashboard extends Controller {
  public static void index() {

    Trainer trainer = Accounts.getLoggedInTrainer();
    List<Member> members = Member.findAll();
    List<GymClass> gymclasses = GymClass.findAll();
    List<Appointment> appointments = trainer.appointments;
    Logger.info("Rendering Trainer Dashboard for " + trainer);
    render("trainerdashboard.html", trainer, members, gymclasses, appointments);
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


  public static void deleteSession(Long gymClassId, Long sessionId)
  {
    GymClass gymClass = GymClass.findById(gymClassId);
    Session session = Session.findById(sessionId);
    gymClass.sessions.remove(session);
    gymClass.save();
    session.delete();
    Logger.info("Deleting " + session.name + " from " + gymClass.name);
    redirect("/trainerdashboard");
  }

  public static void gymClassDetails(Long id) {
    GymClass gymclass = GymClass.findById(id);
    List<Session> sessions = gymclass.sessions;
    for (Session session : sessions) {
      Logger.info("Show sessions: " + session.members.size());

    }


      Logger.info("Rendering details for "  + gymclass.name);
    render("gymclassdetails.html", gymclass);
  }

  public static void editGymClassDetails(Long id, GymClass gymclass1) {
    Logger.info("id: ", id);
    Logger.info("gymclass1: ", gymclass1);
    GymClass gymclass = GymClass.findById(id);

    gymclass.date = gymclass1.date;
    gymclass.endDate = gymclass1.endDate;
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
    render(new Object[]{"addgymclass.html"});
  }

  public static void addGymClass(String name, double duration, String timeSlot, int capacity,
                                 int weeks, String difficulty, String date, String endDate) throws ParseException {
    Logger.info("Creating Gym Class " + name);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    GymClass gymclass = new GymClass(name, duration, timeSlot, capacity, weeks, difficulty, date, endDate);


    gymclass.save();
    index();
    redirect("/trainerdashboard");
  }


  public static void addSession(Long id, String date) throws ParseException {

    Logger.info("gymClassId: " + id);
    Logger.info("sessionDate: " + date);
    GymClass gymClass = GymClass.findById(id);

    Logger.info("gymClass: " + gymClass.name);
    Session newSession = new Session(date, gymClass.name, gymClass.capacity, gymClass.timeSlot);
    gymClass.addSession(newSession);
    gymClass.save();
    redirect("/trainerdashboard");
  }

  public static void editAppointment(Long id) {
    Appointment appointment = Appointment.findById(id);


    Logger.info("Rendering details for "  + appointment.member);
    render("editappointment.html", appointment);
  }

  public static void addAppointment(Long id, Appointment appointment1) {
    Logger.info("id: ", id);
    Logger.info("appointment1: ", appointment1);
    Appointment appointment = Appointment.findById(id);

    appointment.date = appointment1.date;
    appointment.time = appointment1.time;
   // appointment.trainer = appointment1.trainer;
  //  appointment.member = appointment1.member;
    appointment.status = appointment1.status;


    appointment.save();
    Logger.info("Updating appointment " + appointment.status);
    redirect("/trainerdashboard");
  }


}