package org.mehaexample.asdDemo.model.alignadmin;

import java.util.Date;

public class CompanyRatio {
  private int year;
  private int count;

  public CompanyRatio(int year, int count) {
    this.year = year;
    this.count = count;
  }

  public int getYear() {
    return this.year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
    Date date = new Date();
  }



}
