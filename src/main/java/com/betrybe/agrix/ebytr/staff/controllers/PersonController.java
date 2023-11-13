package com.betrybe.agrix.ebytr.staff.controllers;

import com.betrybe.agrix.ebytr.staff.controllers.dto.PersonCreationDto;
import com.betrybe.agrix.ebytr.staff.controllers.dto.PersonResponseDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.security.Role;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
  * PersonController.
  */
@RestController
public class PersonController {

  private final PersonService personService;

  /**
   // * PersonController.
   // */
  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
    * PersonController.
    */

  @PostMapping("/persons")
  public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonCreationDto
      personCreationDto) {
    Person person = personCreationDto.toEntity();
    Person savedPerson = personService.insert(person);

    PersonResponseDto personResponseDto = PersonResponseDto.toDto(savedPerson);

    return ResponseEntity.status(201).body(personResponseDto);
  }

}