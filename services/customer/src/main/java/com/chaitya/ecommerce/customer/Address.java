package com.chaitya.ecommerce.customer;


import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class Address {
    private String steet;
    private String houseNumber;
    private String zipCode;
}
