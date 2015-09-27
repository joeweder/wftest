package com.acme.wftest.domain;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class PersonTest
{
  private static Validator validator;

  @BeforeClass
  public static void setUp()
  {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidation() throws Exception
  {
    Person person = new Person();

    Set<ConstraintViolation<Person>> constraintViolations = validator.validate(person);

    assertEquals("expecting all fields to be invalide", 3, constraintViolations.size());
    for(ConstraintViolation<Person> constraintViolation : constraintViolations)
    {
      assertEquals("may not be null", constraintViolation.getMessage());
    }

    person.setFirstName("firstName");
    person.setLastName("lastName");
    person.setEmail("email");

    assertTrue("expecting no violations", validator.validate(person).isEmpty());
  }
}