package org.JG95.utils;

import org.JG95.exceptions.InvalidEmailException;
import org.JG95.exceptions.InvalidPhoneNumberException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ContactValidationService {

    public void validatePhone(String phone) {
        String regexPattern = "^((\\+7|7|8)+([0-9]){10})$";
        if (!Pattern.compile(regexPattern).matcher(phone).matches()) {
            throw new InvalidPhoneNumberException("Invalid phone number");
        }
    }

    public void validateEmail(String email) {
        String regexPattern = "^(\\S+)@([a-z0-9-]+)(\\.)([a-z]{2,4})(\\.?)([a-z]{0,4})+$";
        if (!Pattern.compile(regexPattern).matcher(email).matches()) {
            throw new InvalidEmailException("Invalid email address");
        }
    }
}
