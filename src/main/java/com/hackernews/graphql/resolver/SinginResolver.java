package com.hackernews.graphql.resolver;

import com.hackernews.graphql.dataclasses.SigninPayload;
import com.hackernews.graphql.dataclasses.User;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class SinginResolver {

	@GraphQLQuery
	public User user(@GraphQLContext SigninPayload payload) {
		return payload.getUser();
	}
}
