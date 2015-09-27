package com.acme.wftest.dao;

import com.acme.wftest.domain.Person;

import java.util.List;

public interface PersonDao
{
  Person insert(Person person);
  Person findById(long id);
  List<Person> findByCriteria(PersonCriteria criteria);
  List<Person> findByLastName(String name);
  Person update(Person person);
  void deleteById(long id);
}
