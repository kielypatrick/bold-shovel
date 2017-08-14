package models;

import org.joda.time.LocalDate;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.*;
import java.util.List;

@Entity
public class GymClass extends Model
{
    @ManyToMany(mappedBy = "gymclasses")
    public List<Member> members = new ArrayList<Member>();

  //  @OneToMany
  //public List<Date> dates = new ArrayList<Date>();


    public String name;
    public double duration;
    public String timeSlot;
    public int capacity;
    public int weeks;
    public String difficulty;
    public String date;
    public Calendar calendar;





    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM, E, HH");


    public GymClass(String name, double duration, String timeSlot, int capacity, int weeks, String difficulty, String date)
    {
        this.name = name;
        this.duration = duration;
        this.timeSlot = timeSlot;
        this.capacity = capacity;
        this.weeks = weeks;
        this.difficulty = difficulty;



        this.members = new ArrayList<Member>();
        this.date = date;
        //this.dates = new ArrayList<Date>();





    }

    public static void nextClass()
    {

    }






    public void addMember(Member member)
    {

        members.add(member);
    }


}
