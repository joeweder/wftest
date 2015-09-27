package com.acme.wftest.persistence;

import com.acme.wftest.dao.PersonCriteria;
import com.acme.wftest.domain.Person;

import java.util.List;

public interface PersonMapper
{
  void insert(Person person);
  Person findById(long id);
  List<Person> findByCriteria(PersonCriteria criteria);
  List<Person> findByLastName(String name);
  void update(Person person);
  void deleteById(long id);
}
