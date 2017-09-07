package utils;

/**
 * The category is determined by the magnitude of the members BMI according to the following:
 *
 *     BMI less than    15   (exclusive)                      is "VERY SEVERELY UNDERWEIGHT"
 *     BMI between      15   (inclusive) and 16   (exclusive) is "SEVERELY UNDERWEIGHT"
 *     BMI between      16   (inclusive) and 18.5 (exclusive) is "UNDERWEIGHT"
 *     BMI between      18.5 (inclusive) and 25   (exclusive) is "NORMAL"
 *     BMI between      25   (inclusive) and 30   (exclusive) is "OVERWEIGHT"
 *     BMI between      30   (inclusive) and 35   (exclusive) is "MODERATELY OBESE"
 *     BMI between      35   (inclusive) and 40   (exclusive) is "SEVERELY OBESE"
 *     BMI greater than 40   (inclusive)                      is "VERY SEVERELY OBESE"
 */
public enum BMI
{

  //use this for the slides/notes:
  //   http://javarevisited.blogspot.ie/2011/08/enum-in-java-example-tutorial.html


  //first index (inclusive) second index (exclusive)
  VERY_SEVERELY_UNDERWEIGHT (0.0, 15.0) {
    @Override
    public String toString(){ return "Very Severely Underweight";}
  },

  SEVERELY_UNDERWEIGHT      (15.0, 16.0) {
    @Override
    public String toString(){ return "Severely Underweight";}
  },

  UNDERWEIGHT               (16, 18.5) {
    @Override
    public String toString(){ return "Underweight";}
  },

  NORMAL                    (18.5, 25) {
    @Override
    public String toString(){ return "Normal";}
  },

  OVERWEIGHT                (25,   30) {
    @Override
    public String toString(){ return "Overweight";}
  },

  MODERATELY_OBESE          (30,   35) {
    @Override
    public String toString(){ return "Moderately Obese";}
  },

  SEVERELY_OBESE            (35,   40) {
    @Override
    public String toString(){ return "Severely Obese";}
  },

  VERY_SEVERELY_OBESE       (40,   1000) {
    @Override
    public String toString(){ return "Very Severely Obese";}
  };

  private double rangeLow;
  private double rangeHigh;

  private BMI(double rangeLow, double rangeHigh) {
    this.rangeLow = rangeLow;
    this.rangeHigh = rangeHigh;
  }

  public boolean bmiCategory(double bmiValue){
    if ((bmiValue >= this.rangeLow) && (bmiValue < this.rangeHigh)){
      return true;
    }
    return false;
  }

}
