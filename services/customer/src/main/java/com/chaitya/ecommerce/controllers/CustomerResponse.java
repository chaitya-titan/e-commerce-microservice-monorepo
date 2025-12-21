package com.chaitya.ecommerce.controllers;


import com.chaitya.ecommerce.customer.Address;

public record CustomerResponse (
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
