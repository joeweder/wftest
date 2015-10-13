package com.acme.wftest.web;

import com.acme.wftest.dao.PersonCriteria;
import com.acme.wftest.domain.Person;
import com.acme.wftest.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

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
  public ResponseEntity<String> insert(@RequestBody @Valid Person person)
  {
    Person addedPerson = getPersonService().insert(person);

    return ResponseEntity
      .created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addedPerson.getId()).toUri())
      .cacheControl(CacheControl.noStore())
      .body("Success");
  }

  @RequestMapping(method = RequestMethod.GET)
  public List<Person> find(@RequestParam(value="name", required = false) String name, @RequestParam(value="descending", required = false) Boolean descending)
  {
    if(null != name && name.isEmpty())
      name = null;

    return getPersonService().findByCriteria(new PersonCriteria(name, descending));
  }
}
