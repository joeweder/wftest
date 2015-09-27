package com.acme.wftest.web;

import com.acme.wftest.dao.PersonCriteria;
import com.acme.wftest.domain.Person;
import com.acme.wftest.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/person")
public class PersonController
{
  static Logger logger = LoggerFactory.getLogger(PersonController.class);

  @Autowired
  private PersonService personService;

  public PersonService getPersonService()
  {
    return personService;
  }

  public void setPersonService(PersonService personService)
  {
    this.personService = personService;
  }

  @RequestMapping(method=RequestMethod.POST)
  public String insert(@RequestBody @Valid Person person)
  {
    try
    {
      getPersonService().insert(person);
      return "Success";
    }
    catch(Exception e)
    {
      if(e.getCause() instanceof SQLIntegrityConstraintViolationException)
        return "Failed to insert: email already used.";

      return "Failed to insert due to error: " + e.getMessage();
    }
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<Person> find(@RequestParam(value="name", required = false) String name, @RequestParam(value="descending", required = false) Boolean descending)
  {
    if(null != name && name.isEmpty())
      name = null;

    List<Person> results = getPersonService().findByCriteria(new PersonCriteria(name, descending));

    return results;
  }
}
