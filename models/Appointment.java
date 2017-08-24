package models;

import play.db.jpa.Model;


import javax.persistence.Entity;
import javax.persistence.Lob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Patrick on 16/08/2017.
 */

@Entity
public class Appointment extends Model {
    public String date;
    public String time;
    //public Trainer trainer;
  //  public Member member;
    //  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //  Date date1 = sdf.parse(date);

    public Appointment(String date, String time) throws ParseException {
        this.date = date;
        this.time = time;
      //  this.trainer = trainer;
        //this.member = member;
        //    this.date1 = date1;

    }


}

