package com.dwdponto04.stockflow.infrastructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException resourceNotFoundException){

        return buildErrorResponse(HttpStatus.NOT_FOUND,
                resourceNotFoundException.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ConflictException conflictException){

        return buildErrorResponse(HttpStatus.CONFLICT,
                conflictException.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(
            InvalidPasswordException invalidPasswordException){

        return buildErrorResponse(HttpStatus.UNAUTHORIZED,
                invalidPasswordException.getMessage(),
                List.of());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException illegalArgumentException){

        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                illegalArgumentException.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException){

        List<FieldErrorResponse> errorResponses =
                methodArgumentNotValidException.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> new FieldErrorResponse(
                                error.getField(),
                                error.getDefaultMessage()
                        ))
                        .toList();
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Error de validação",
                errorResponses
        );
    }

    @ExceptionHandler (MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException methodArgumentTypeMismatchException){

        String message = String.format(
                "O parâmetro '%s' deve ser do tipo %s",
                methodArgumentTypeMismatchException.getName(),
                methodArgumentTypeMismatchException.getRequiredType().getSimpleName()
        );

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                List.of()
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException httpMessageNotReadableException){

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Corpo da requisição inválido",
                List.of()
        );
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
            MissingServletRequestParameterException missingServletRequestParameterException){

        String message = String.format(
                "O parâmetro '%s' é obrigatório",
                missingServletRequestParameterException.getParameterName()
        );
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                message,
                List.of()
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception exception){

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno no servidor",
                List.of()
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            List<FieldErrorResponse> errors){

        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(
                        status.value(),
                        message,
                        errors
                ));
    }

}
