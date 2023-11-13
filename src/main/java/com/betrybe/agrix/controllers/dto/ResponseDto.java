package com.betrybe.agrix.controllers.dto;

/**
 * Farm.
 */
public record ResponseDto<T>(String message, T data) { }
