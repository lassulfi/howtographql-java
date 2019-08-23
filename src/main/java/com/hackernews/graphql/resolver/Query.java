package com.hackernews.graphql.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.repository.LinkRepository;

public class Query implements GraphQLRootResolver {

	private final LinkRepository linkRepository;
	
	public Query(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}
	
	public List<Link> allLinks() {
		return linkRepository.getAllLinks();
	}
}
