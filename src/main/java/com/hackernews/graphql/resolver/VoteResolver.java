package com.hackernews.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.dataclasses.User;
import com.hackernews.graphql.dataclasses.Vote;
import com.hackernews.graphql.repository.LinkRepository;
import com.hackernews.graphql.repository.UserRepository;

public class VoteResolver implements GraphQLResolver<Vote> {

	private final LinkRepository linkRepository;
	private final UserRepository userRepository;
	
	public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
		this.linkRepository = linkRepository;
		this.userRepository = userRepository;
	}
	
	public User user(Vote vote) {
		return userRepository.findById(vote.getUserId());
	}
	
	public Link link(Vote vote) {
		return linkRepository.getById(vote.getLinkId());
	}
}
