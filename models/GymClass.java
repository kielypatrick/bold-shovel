package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Lob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class GymClass extends Model
{
    @ManyToMany(mappedBy = "gymclasses")
    public List<Member> members;

    public String name;
    public double duration;
    public String timeSlot;
    public int capacity;
    public int weeks;
    public String difficulty;



    @Lob
    public String instructor;
    public Date startDate;

    public GymClass(String name, double duration, String timeSlot, int capacity, int weeks, String difficulty)
    {
        this.name = name;
        this.duration = duration;
        this.timeSlot = timeSlot;
        this.capacity = capacity;
        this.weeks = weeks;
        this.difficulty = difficulty;

        this.members = new ArrayList<Member>();

        this.startDate = new Date();
    }

    public void addMember(Member member)
    {

        members.add(member);
    }
}
