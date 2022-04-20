package com.gorokhov.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorControllerAdvice {

    @ExceptionHandler(NotFoundException.class)  //handle this exception
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String notFoundExceptionException(final NotFoundException e, final Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("status", "INTERNAL_SERVER_ERROR");
        //custom message to render in HTML
        return "error";
    }

    @ExceptionHandler(DeleteRoleException.class)  //handle this exception
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String deleteRoleExceptionException(final DeleteRoleException e, final Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("status", "INTERNAL_SERVER_ERROR");
        return "error";
    }

}
