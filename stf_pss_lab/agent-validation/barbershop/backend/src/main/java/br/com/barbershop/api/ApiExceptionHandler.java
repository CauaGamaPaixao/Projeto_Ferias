package br.com.barbershop.api;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
 public record ApiError(Instant timestamp,int status,String message,Map<String,String> fields) {}
 @ExceptionHandler(MethodArgumentNotValidException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
 ApiError validation(MethodArgumentNotValidException ex){Map<String,String> fields=new LinkedHashMap<>();ex.getBindingResult().getFieldErrors().forEach(e->fields.putIfAbsent(e.getField(),e.getDefaultMessage()));return new ApiError(Instant.now(),400,"Verifique os campos informados.",fields);}
 @ExceptionHandler(ResponseStatusException.class) ResponseEntity<ApiError> status(ResponseStatusException ex){return ResponseEntity.status(ex.getStatusCode()).body(new ApiError(Instant.now(),ex.getStatusCode().value(),ex.getReason(),Map.of()));}
 @ExceptionHandler(DataIntegrityViolationException.class) @ResponseStatus(HttpStatus.CONFLICT) ApiError duplicate(){return new ApiError(Instant.now(),409,"Já existe um cadastro com esse nome.",Map.of());}
}
