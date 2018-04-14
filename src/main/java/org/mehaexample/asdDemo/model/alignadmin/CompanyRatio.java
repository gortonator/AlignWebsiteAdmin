package org.mehaexample.asdDemo.model.alignadmin;

public class CompanyRatio {
  private String companyName;
  private int count;

  public CompanyRatio(String companyName, int count) {
    this.companyName = companyName;
    this.count = count;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
}
