package com.hoaxify.hoaxify.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.hoaxify.user.vm.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.hoaxify.hoaxify.error.ApiError;
import com.hoaxify.hoaxify.shared.GenericResponse;

@RestController
@RequestMapping("/api/1.0")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("User saved");
    }

    @GetMapping("/users")
    Page<UserVM> getUsers() {
        return userService.getUsers().map(UserVM::new);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(400, "Validation error", request.getServletPath());

        BindingResult result = exception.getBindingResult();

        Map<String, String> validationErrors = new HashMap<>();

        for(FieldError fieldError: result.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);

        return apiError;
    }
}