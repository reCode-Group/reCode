package com.dev.reCode.validator;

import com.dev.reCode.models.User;
import com.dev.reCode.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return  User.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        System.out.print(user.getEmail()+ " asdsadasd");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (user.getName().length() > 100) {
            errors.rejectValue("name", "Size.userForm.name");
        }
        if (userService.findUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "Size.userForm.password");
        }


    }
}
