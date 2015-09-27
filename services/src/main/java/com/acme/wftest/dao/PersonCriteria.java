package com.acme.wftest.dao;

public class PersonCriteria
{
  private String lastName;
  private Boolean descending;

  public PersonCriteria()
  {
  }

  public PersonCriteria(String lastName)
  {
    this.lastName = lastName;
  }

  public PersonCriteria(String lastName, Boolean descending)
  {
    this.lastName = lastName;
    this.descending = descending;
  }

  public PersonCriteria(Boolean descending)
  {
    this.descending = descending;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public Boolean getDescending()
  {
    return descending;
  }

  public void setDescending(Boolean descending)
  {
    this.descending = descending;
  }
}
