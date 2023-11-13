package com.betrybe.agrix.ebytr.staff.controllers.dto;

import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.security.Role;

/**
 * PersonDto.
 */
public record PersonCreationDto(String username, String password, Role role) {

  /**
   * toEntity.
   */
  public Person toEntity() {
    Person person = new Person();
    person.setUsername(username);
    person.setRole(role);
    person.setPassword(password);
    return person;
  }
}