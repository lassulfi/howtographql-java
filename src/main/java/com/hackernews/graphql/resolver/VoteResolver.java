package com.hackernews.graphql.resolver;

import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.dataclasses.User;
import com.hackernews.graphql.dataclasses.Vote;
import com.hackernews.graphql.repository.LinkRepository;
import com.hackernews.graphql.repository.UserRepository;

import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class VoteResolver {

	private final LinkRepository linkRepository;
	private final UserRepository userRepository;
	
	public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
	}
	
	@GraphQLQuery
	public User user(@GraphQLContext Vote vote) {
		return userRepository.findById(vote.getUserId());
	}
	
	@GraphQLQuery
	public Link link(@GraphQLContext Vote vote) {
		return linkRepository.getById(vote.getLinkId());
	}
}
