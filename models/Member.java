package models;

import play.Logger;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends Model
{
  public String email;
  public String name;
  public String password;
  public String address;
  public String gender;
  public double height;
  public double startingweight;

  @OneToMany(cascade = CascadeType.ALL)
  public List<Assessment> assessments = new ArrayList<Assessment>();

  @ManyToMany
  public List<GymClass> gymclasses;

  public Member(String email, String name, String password, String address, String gender, double height, double startingweight)
  {
    this.email = email;
    this.name = name;
    this.password = password;
    this.address = address;
    this.gender = gender;
    this.height = height;
    this.startingweight = startingweight;
  }

  public static Member findByEmail(String email)
  {
    return find("email", email).first();
  }

  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }

  public void addGymClass(GymClass gymClass, Member member)
  {
    gymclasses.add(gymClass);
    Logger.info("Adding " + member.name + " to " + gymClass.name);
  }

  public void removeGymClass(GymClass gymClass)
  {
    gymclasses.remove(gymClass);
  }
}
