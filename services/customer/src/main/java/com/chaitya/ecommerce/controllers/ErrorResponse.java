package com.chaitya.ecommerce.controllers;

import java.util.Map;

public record ErrorResponse (
        Map<String, String> errors
) {
}
