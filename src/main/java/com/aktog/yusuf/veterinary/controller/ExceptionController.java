package com.aktog.yusuf.veterinary.controller;


import com.aktog.yusuf.veterinary.exception.EmailAlreadyExistsException;
import com.aktog.yusuf.veterinary.exception.PhoneNumberAlreadyExistsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String emailAlreadyExistsException(EmailAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex);
        model.addAttribute("code", 409);

        return "error";
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public String phoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex, Model model) {
        model.addAttribute("error", ex);
        model.addAttribute("code", 409);

        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedException(AccessDeniedException ex, Model model) {

        model.addAttribute("error", ex);
        model.addAttribute("code", 403);


        return "error";
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public String internalServerException(HttpServerErrorException.InternalServerError ex, Model model) {
        model.addAttribute("error", ex);
        model.addAttribute("code", 500);

        return "error";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundException(EntityNotFoundException ex, Model model) {
        model.addAttribute("error", ex);
        model.addAttribute("code", 404);


        return "error";
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public String badRequestException(HttpClientErrorException.BadRequest ex, Model model) {
        model.addAttribute("error", ex);
        model.addAttribute("code", 400);
        return "error";
    }
}
