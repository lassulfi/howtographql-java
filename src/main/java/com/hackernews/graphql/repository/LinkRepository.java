package com.hackernews.graphql.repository;

import java.util.ArrayList;
import java.util.List;

import com.hackernews.graphql.dataclasses.Link;

public class LinkRepository {

	private final List<Link> links;
	
	public LinkRepository() {
		links = new ArrayList<Link>();
		links.add(new Link("http://howtographql.com", "Your favorite GraphQL page!"));
		links.add(new Link("http://howtographql.org/learn", "The official docks"));
	}
	
	public List<Link> getAllLinks() {
		return links;
	}
	
	public void saveLink(Link link) {
		links.add(link);
	}
	
}
