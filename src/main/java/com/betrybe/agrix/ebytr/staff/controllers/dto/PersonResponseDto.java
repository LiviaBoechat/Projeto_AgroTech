package com.betrybe.agrix.ebytr.staff.controllers.dto;

import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.security.Role;

/**
 * PersonDto.
 */
public record PersonResponseDto(Long id, String username, Role role) {

  /**
   * toDto.
   */
  public static PersonResponseDto toDto(Person person) {
    PersonResponseDto personResponseDto = new PersonResponseDto(
        person.getId(), person.getUsername(), person.getRole()
    );
    return  personResponseDto;
  }
}