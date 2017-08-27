package controllers;

import models.*;
import play.Logger;
import play.mvc.Controller;
import utils.Analytics;
import utils.MemberStats;


import java.text.ParseException;
import java.util.*;


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

  public static void bookAssessment()
  {

    List<Trainer> trainers = Trainer.findAll();
    render("bookassessment.html", trainers);
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



  public static void gymClassDetails(Long id) {
    GymClass gymclass = GymClass.findById(id);
    Logger.info("sessions: " + gymclass.getSessions());

    Logger.info( "Rendering class details for " + gymclass.name);
    render("membergymclassdetails.html", gymclass);

  }

  public static void addGymClass(Long id) {

    GymClass gymclass = GymClass.findById(id);
    List<Session> sessions = gymclass.sessions;
    Member member = Accounts.getLoggedInMember();

    for (Session session : sessions) {
      if ((session.inFuture == true) && (session.members.size() < session.capacity)) {
        if (member.sessions.contains(session)) {
          Logger.info(member.name + " in " + session.name + "already duuuuh!");
          member.sessions.remove(session);
        }
        member.sessions.add(session);
        member.save();
        Logger.info("Enrollin " + member.name + " in " + sessions);
      }
    }
    redirect("/dashboard");


  }

  public static void addSession(Long id)
  {
    Session session = Session.findById(id);

    Member member = Accounts.getLoggedInMember();
    if (member.sessions.contains(session)) {
      Logger.info(member.name + " in " + session.name + "already duuuuh!");
      redirect("/dashboard");
    }
      member.addSession(session, member);
    member.save();
    session.save();
    Logger.info("Enrollin " + member.name + " in " + session.gymClassName + " for "
            + session.name + ". Total of" + session.members.size());
    redirect("/dashboard");
  }

  public static void quitSession(Long sessionid, Long memberid)
  {
    Member member = Member.findById(memberid);
    Session session = Session.findById(sessionid);
    member.sessions.remove(session);
    member.save();
    //Logger.info("Removing " + member.name + " from " + session.gymClassName + " for " + session.name);
    redirect("/dashboard");


  }

  public static void requestAppointment(Long trainerid, String date, String time) throws ParseException {
    Member member = Accounts.getLoggedInMember();
    Trainer trainer = Trainer.findById(trainerid);
    String status = "pending...";
    Appointment appointment = new Appointment(date, time, trainer.name, member.name, status, trainer.email);

    if(member.appointment != null) {
      Logger.info("Removing existing appointment for" + member.name);
      cancelAppointment(member.appointment.getId(), member.getId());
    }

    trainer.appointments.add(appointment);
    member.appointment = appointment;
    member.save();
    trainer.save();
    Logger.info("Requestion appointment for " + member.name + " with " + appointment.trainer + " for "
            + appointment.date + " at " + appointment.time);
    redirect("/dashboard");
  }

  public static void cancelAppointment(Long appointmentid, Long memberid)
  {
    Member member = Member.findById(memberid);
    Appointment appointment = Appointment.findById(appointmentid);
    Trainer trainer = Trainer.findByEmail(appointment.trainerEmail);
    Logger.info("Removing " + member.name  + "'s appointment booking ");
    member.appointment = null;
    trainer.appointments.remove(appointment);

    member.save();
    trainer.save();
    appointment.delete();
    redirect("/dashboard");
  }









}
