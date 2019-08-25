package com.hackernews.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.dataclasses.User;
import com.hackernews.graphql.repository.UserRepository;

public class LinkResolver implements GraphQLResolver<Link> {
	
	private final UserRepository userRepository;
	
	public LinkResolver(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User postedBy(Link link) {
		if(link.getUserId() == null) {
			return null;
		}
		
		return userRepository.findById(link.getUserId());
	}
}
