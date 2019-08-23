package com.hackernews.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.repository.LinkRepository;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public Mutation(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link createLink(String url, String description) {
        Link newLink = new Link(url, description);

        this.linkRepository.saveLink(newLink);

        return newLink;
    }
}
