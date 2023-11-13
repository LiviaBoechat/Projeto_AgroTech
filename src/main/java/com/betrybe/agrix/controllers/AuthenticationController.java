package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.AuthenticationDto;
import com.betrybe.agrix.controllers.dto.ResponseTokenDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final TokenService tokenService;

  /**
   * Auth.
   */
  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * Auth.
   */
  @PostMapping("/login")
  public ResponseEntity<ResponseTokenDto> login(@RequestBody AuthenticationDto authenticationDto) {

    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(authenticationDto.username(),
            authenticationDto.password());
    Authentication auth = authenticationManager.authenticate(usernamePassword);
    Person person = (Person) auth.getPrincipal();

    String token = tokenService.generateToken(person);

    ResponseTokenDto response = new ResponseTokenDto("Pessoa autenticada com sucesso!", token);


    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}