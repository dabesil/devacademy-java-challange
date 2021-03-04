package br.com.casamagalhaes.workshop.desafio.resources.exception;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Pedido Não Encontrado",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<StandardError> unsupportedOperation(UnsupportedOperationException e,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Operação Não Suportada",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> validationException(ConstraintViolationException e, 
            HttpServletRequest request){
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Operação Não Suportada",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);                 
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<StandardError> httpException(HttpClientErrorException e, 
            HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(System.currentTimeMillis(), status.value(), "Operação Não Suportada",
                e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err); 
                        
    }

}  
