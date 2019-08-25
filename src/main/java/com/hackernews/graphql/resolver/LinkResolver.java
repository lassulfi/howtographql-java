package com.hackernews.graphql.resolver;

import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.dataclasses.User;
import com.hackernews.graphql.repository.UserRepository;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class LinkResolver {
	
	private final UserRepository userRepository;
	
	public LinkResolver(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GraphQLQuery
	public User postedBy(@GraphQLContext Link link) {
		if(link.getUserId() == null) {
			return null;
		}
		
		return userRepository.findById(link.getUserId());
	}
}
