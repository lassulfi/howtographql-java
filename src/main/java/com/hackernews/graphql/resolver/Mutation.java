package com.hackernews.graphql.resolver;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hackernews.graphql.config.AuthContext;
import com.hackernews.graphql.dataclasses.AuthData;
import com.hackernews.graphql.dataclasses.Link;
import com.hackernews.graphql.dataclasses.SigninPayload;
import com.hackernews.graphql.dataclasses.User;
import com.hackernews.graphql.dataclasses.Vote;
import com.hackernews.graphql.repository.LinkRepository;
import com.hackernews.graphql.repository.UserRepository;
import com.hackernews.graphql.repository.VoteRepository;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public Link createLink(String url, String description, DataFetchingEnvironment env) {
    	AuthContext context = env.getContext();
    	
        Link newLink = new Link(url, description, context.getUser().getId());

        this.linkRepository.saveLink(newLink);

        return newLink;
    }
    
    public User createUser(String name, AuthData auth) {
    	User newUser = new User(name, auth.getEmail(), auth.getPassword());
    	
    	return userRepository.saveUser(newUser);
    }
    
    public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
    	User user = this.userRepository.findByEmail(auth.getEmail());
    	if(user.getPassword().equals(auth.getPassword())) {
    		return new SigninPayload(user.getId(), user);
    	}
    	throw new GraphQLException("Invalid credentials");
    }
    
    public Vote createVote(String linkId, String userId) {
    	ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
    	
    	return voteRepository.saveVote(new Vote(now, userId, linkId));
    }
}
