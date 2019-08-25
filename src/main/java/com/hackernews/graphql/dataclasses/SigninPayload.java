package com.hackernews.graphql.dataclasses;

public class SigninPayload {

	private final String token;
	private final User user;
	
	public SigninPayload(String token, User user) {
		super();
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}
}
