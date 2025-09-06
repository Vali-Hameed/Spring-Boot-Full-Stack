package com.FullStack.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
