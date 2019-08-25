package com.hackernews.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.hackernews.graphql.dataclasses.SigninPayload;
import com.hackernews.graphql.dataclasses.User;

public class SinginResolver implements GraphQLResolver<SigninPayload> {

	public User user(SigninPayload payload) {
		return payload.getUser();
	}
}
