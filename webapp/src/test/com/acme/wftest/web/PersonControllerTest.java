package com.acme.wftest.web;

import com.acme.wftest.config.AppConfig;
import com.acme.wftest.domain.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("developer")
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class PersonControllerTest
{
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

//  @Mock
//  private PersonService personService;

  @Before
  public void setUp() throws Exception
  {
    MockitoAnnotations.initMocks(this);
//    PersonController controller = new PersonController();
//    controller.setPersonService(personService);
//    Using webAppContextSetup(wac) technique, not able to inject the mock personService
//    It works but it becomes an integration test and the personService goes to the database
//    11.3.6 Spring MVC Test Framework says the webAppContextSetup(wac) is much more complete test of the configuration
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void testFind() throws Exception
  {
//    Person p = new Person();
//    p.setLastName("TestLastName");
//
//    List<Person> results = new ArrayList<>();
//    results.add(p);

//    when(personService.findByCriteria(any(PersonCriteria.class))).thenReturn(results);

    ResultActions resultActions = mockMvc.perform(get("/person").param("name", "Jones")
      .accept(MediaType.parseMediaType("application/json;charset=UTF-8")));
    resultActions.andExpect(status().isOk())
      .andExpect(content().contentType("application/json;charset=UTF-8"))
      .andExpect(jsonPath("$..[0].lastName").value("Jones"));
  }

  @Test
  public void testInsert() throws Exception
  {
    ObjectMapper mapper = new ObjectMapper();

    Person p = new Person();
    p.setLastName("lastName");

    //Expecting failure since the Person object should not pass @Valid
    ResultActions resultActions = mockMvc.perform(post("/person")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(p))
      .accept(MediaType.parseMediaType("application/json;charset=UTF-8")));
    resultActions.andExpect(status().is4xxClientError());

    p.setFirstName("firstName");
    p.setEmail("bobjones@mail.com");

    //Expecting failure because the email address should a violate unique constraint
    resultActions = mockMvc.perform(post("/person")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(p))
      .accept(MediaType.parseMediaType("application/json;charset=UTF-8")));
    resultActions.andExpect(status().is4xxClientError());

    p.setEmail("unique@mail.com");

    //Expecting pass
    resultActions = mockMvc.perform(post("/person")
      .contentType(MediaType.APPLICATION_JSON)
      .content(mapper.writeValueAsString(p))
      .accept(MediaType.parseMediaType("application/json;charset=UTF-8")));
    resultActions.andExpect(status().is(HttpStatus.CREATED.value()));

  }
}