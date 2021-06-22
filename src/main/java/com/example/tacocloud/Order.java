package com.example.tacocloud;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class Order {
    @NotBlank(message = "Name required to order")
    private String name;
    @NotBlank(message = "Please give street address")
    private String street;
    @NotBlank(message = "City required")
    private String city;
    @NotBlank(message = "State required")
    private String state;
    @Digits(integer = 5, fraction = 0, message = "Valid zip code required")
    private String zip;
    @CreditCardNumber(message = "Enter a valid credit card number")
    private String ccNumber;
    @Pattern(regexp = "(^0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;

}
