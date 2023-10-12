package io.rainett.deliverybackend.dto;

public record SignUpDto(String email, char[] password, String firstName, String lastName) {
}
