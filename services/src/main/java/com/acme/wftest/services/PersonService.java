package com.acme.wftest.services;

import com.acme.wftest.dao.PersonCriteria;
import com.acme.wftest.domain.Person;

import java.util.List;

public interface PersonService
{
  Person insert(Person person);
  Person findById(long id);
  List<Person> findByCriteria(PersonCriteria criteria);
  List<Person> findByLastName(String name);
  Person update(Person person);
  void deleteById(long id);
}
