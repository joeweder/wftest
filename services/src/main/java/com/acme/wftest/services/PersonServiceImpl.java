package com.acme.wftest.services;

import com.acme.wftest.dao.PersonCriteria;
import com.acme.wftest.dao.PersonDao;
import com.acme.wftest.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService
{
  private PersonDao personDao;

  public PersonDao getPersonDao()
  {
    return personDao;
  }

  @Autowired
  public void setPersonDao(PersonDao personDao)
  {
    this.personDao = personDao;
  }

  @Override
  public Person insert(Person person)
  {
    return getPersonDao().insert(person);
  }

  @Override
  public Person findById(long id)
  {
    return getPersonDao().findById(id);
  }

  @Override
  public List<Person> findByCriteria(PersonCriteria criteria)
  {
    return getPersonDao().findByCriteria(criteria);
  }

  @Override
  public List<Person> findByLastName(String name)
  {
    return getPersonDao().findByLastName(name);
  }

  @Override
  public Person update(Person person)
  {
    return getPersonDao().update(person);
  }

  @Override
  public void deleteById(long id)
  {
    getPersonDao().deleteById(id);
  }
}
