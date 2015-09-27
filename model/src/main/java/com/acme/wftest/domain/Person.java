package com.acme.wftest.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Person
{
  private long id;
  private String firstName;
  private String lastName;
  private String email;

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  @NotNull
  @Size(min = 1, max = 80)
  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  @NotNull
  @Size(min = 1, max = 80)
  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  @NotNull
  @Size(min = 1, max = 256)
  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }
}
