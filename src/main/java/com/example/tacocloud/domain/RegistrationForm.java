package com.example.tacocloud.domain;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {
    @NotBlank(message="username required")
    @Size(min=3, max=20, message="username must be between 3-20 characters")
    private String username;

    @NotBlank(message="password required")
    @Size(min=6, message="password must be at least 6 characters long")
    private String password;

    @NotNull(message="confirm password must match")
    private String confirm;

    @NotBlank(message="full name required")
    private String fullname;

    @NotBlank(message="street required")
    @Size(max=50, message="Street name too long, 50 character limit")
    private String street;

    @NotBlank(message="city required")
    @Size(max = 50, message="City name too long, 50 character limit")
    private String city;

    @NotBlank(message="state required")
    @Size(max=2, message = "Not a valid state entry, 2 character limit")
    private String state;

    @NotBlank(message="zip required")
    @Digits(integer = 5, fraction = 0, message = "Valid zip code required")
    private String zip;

    @NotBlank(message="phone number required")
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username, passwordEncoder.encode(password),
                fullname, street, city, state, zip, phoneNumber);
    }

    public void setPassword(String password) {
        this.password = password;
        checkPassword();
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
        checkPassword();
    }

    private void checkPassword() {
        if(this.password == null || this.confirm == null){
            return;
        }else if(!this.password.equals(confirm)){
            this.confirm = null;
        }
    }
}