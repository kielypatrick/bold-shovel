package models;

import play.Logger;
import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static controllers.Accounts.login;

@Entity
public class Trainer extends Model
{
  public String email;
  public String password;

  @OneToMany(cascade = CascadeType.ALL)
  public List<Appointment> appointments = new ArrayList<Appointment>();


  public Trainer(String email, String password)
  {
    this.email = email;
    this.password = password;
  }

  public static Trainer findByEmail(String email)
  {
    return find("email", email).first();
  }
  public boolean checkPassword(String password)
  {
    return this.password.equals(password);
  }






}
