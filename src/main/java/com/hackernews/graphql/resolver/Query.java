package com.hackernews.graphql.resolver;

import java.util.List;

import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.dataclasses.LinkFilter;
import com.hackernews.graphql.repository.LinkRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

public class Query {

	private final LinkRepository linkRepository;
	
	public Query(LinkRepository linkRepository) {
		this.linkRepository = linkRepository;
	}
	
	@GraphQLQuery
	public List<Link> allLinks(LinkFilter filter, 
			@GraphQLArgument(name = "skip", defaultValue = "0") Number skip, 
			@GraphQLArgument(name = "first", defaultValue = "0") Number first) {
		return linkRepository.getAllLinks(filter, skip.intValue(), first.intValue());
	}
}
