package com.hackernews.graphql.resolver;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.dataclasses.LinkFilter;
import com.hackernews.graphql.repository.LinkRepository;

public class Query implements GraphQLRootResolver {

	private final LinkRepository linkRepository;
	
	public Query(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}
	
	public List<Link> allLinks(LinkFilter filter, Number skip, Number first) {
		return linkRepository.getAllLinks(filter, skip.intValue(), first.intValue());
	}
}
