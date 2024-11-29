package com.example.myapp.authentication.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * Login response object containing the JWT
 *
 * @author imesha
 */
@Data
@AllArgsConstructor
public class LoginResult {
	@NonNull
	private String token;
}
