//package com.aktog.yusuf.veterinary.controller;
//
//
//import com.aktog.yusuf.veterinary.exception.AddressInUseException;
//import com.aktog.yusuf.veterinary.exception.EmailAlreadyExistsException;
//import com.aktog.yusuf.veterinary.exception.PhoneNumberAlreadyExistsException;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//
//import javax.persistence.EntityNotFoundException;
//
//@ControllerAdvice
//public class ExceptionController {
//
//    @ExceptionHandler(EmailAlreadyExistsException.class)
//    public String emailAlreadyExistsException(EmailAlreadyExistsException ex, Model model) {
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.CONFLICT);
//
//        return "error";
//    }
//
//    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
//    public String phoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex, Model model) {
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.CONFLICT);
//
//
//        return "error";
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public String accessDeniedException(AccessDeniedException ex, Model model) {
//
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.FORBIDDEN);
//
//        return "error";
//    }
//
//    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
//    public String internalServerException(HttpServerErrorException.InternalServerError ex, Model model) {
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
//
//        return "error";
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public String entityNotFoundException(EntityNotFoundException ex, Model model) {
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.NOT_FOUND);
//
//        return "error";
//    }
//
//    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
//    public String badRequestException(HttpClientErrorException.BadRequest ex, Model model) {
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.BAD_REQUEST);
//        return "error";
//    }
//
//    @ExceptionHandler(AddressInUseException.class)
//    public String addressInUseException(AddressInUseException ex, Model model) {
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.UNPROCESSABLE_ENTITY);
//
//        return "error";
//    }
//    @ExceptionHandler(HttpClientErrorException.NotFound.class)
//    public String resourceNotFound(HttpClientErrorException.NotFound ex, Model model) {
//        model.addAttribute("exception", ex);
//        model.addAttribute("status", HttpStatus.NOT_FOUND);
//
//        return "error";
//    }
//
//}
