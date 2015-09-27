package com.acme.wftest.dao;

import com.acme.wftest.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration("classpath:testApplicationContext.xml")
@SqlGroup({
  @Sql(scripts = "/com/acme/wftest/dao/test-person-data.sql",
  executionPhase = BEFORE_TEST_METHOD),
  @Sql(
    scripts = "/com/acme/wftest/dao/reset-person-data.sql",
    executionPhase = AFTER_TEST_METHOD)
})
public class PersonDaoImplTest
{
  @Autowired
  private PersonDao dao;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void testInsert() throws Exception
  {
    assertNull(dao.insert(null));

    Person person = new Person();
    person.setFirstName("firstName");
    person.setLastName("lastName");
    person.setEmail("email@mail.com");

    Person insertedPerson = dao.insert(person);
    assertNotNull(insertedPerson);
    assertEquals(person.getFirstName(), insertedPerson.getFirstName());
    assertEquals(person.getLastName(), insertedPerson.getLastName());
    assertEquals(person.getEmail(), insertedPerson.getEmail());
  }

  @Test
  public void testFindById() throws Exception
  {
    assertTrue(0 < JdbcTestUtils.countRowsInTable(jdbcTemplate, "PERSON"));
    assertNull(dao.findById(0));

    Person person = dao.findById(1);
    assertNotNull(person);
    assertEquals(1, person.getId());
    assertEquals("Bob", person.getFirstName());
    assertEquals("Jones", person.getLastName());
    assertEquals("bobjones@mail.com", person.getEmail());
  }

  @Test
  public void testFindByCriteria() throws Exception
  {
    List<Person> persons = dao.findByCriteria(null);
    assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "PERSON"), persons.size());

    Person[] personArray = persons.toArray(new Person[]{});
    for(int index = 1; index < personArray.length; index++)
    {
      assertTrue("Expected default Ascending LastName ordering", 0 <= personArray[index].getLastName().compareTo(personArray[index - 1].getLastName()));
    }

    persons = dao.findByCriteria(new PersonCriteria("Weder"));
    assertEquals("expected only 2 persons with lastName='Weder' in the test data", 2, persons.size());
    personArray = persons.toArray(new Person[]{});
    assertTrue(0 <= personArray[1].getFirstName().compareTo(personArray[0].getFirstName()));
    assertTrue(0 <= personArray[1].getLastName().compareTo(personArray[0].getLastName()));
  }

  @Test
  public void testFindByName() throws Exception
  {
    List<Person> persons = dao.findByLastName(null);
    assertNotNull(persons);
    assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, "PERSON"), persons.size());
    assertEquals(1, persons.get(0).getId());
    assertEquals("Bob", persons.get(0).getFirstName());
    assertEquals("Jones", persons.get(0).getLastName());
    assertEquals("bobjones@mail.com", persons.get(0).getEmail());

    persons = dao.findByLastName("Jones");
    assertNotNull(persons);
    assertEquals(1, persons.size());
    assertEquals(1, persons.get(0).getId());
    assertEquals("Bob", persons.get(0).getFirstName());
    assertEquals("Jones", persons.get(0).getLastName());
    assertEquals("bobjones@mail.com", persons.get(0).getEmail());
  }

  @Test
  public void testUpdate() throws Exception
  {
    Person person = dao.findById(1);
    assertNotNull(person);
    assertEquals(1, person.getId());
    assertEquals("Bob", person.getFirstName());
    assertEquals("Jones", person.getLastName());
    assertEquals("bobjones@mail.com", person.getEmail());

    person.setFirstName("firstName");
    person.setLastName("lastName");
    person.setEmail("mail@email.com");

    dao.update(person);
    Person updatedPerson = dao.findById(1);
    assertNotNull(updatedPerson);
    assertEquals(person.getId(), updatedPerson.getId());
    assertEquals(person.getFirstName(), updatedPerson.getFirstName());
    assertEquals(person.getLastName(), updatedPerson.getLastName());
    assertEquals(person.getEmail(), updatedPerson.getEmail());
  }

  @Test
  public void testDeleteById() throws Exception
  {
    Person person = dao.findById(100);
    assertNull(person);

    dao.deleteById(100);

    person = dao.findById(1);
    assertNotNull(person);
    assertEquals(1, person.getId());

    dao.deleteById(person.getId());
    assertNull(dao.findById(1));
  }
}