package com.hackernews.graphql.endpoints;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coxautodev.graphql.tools.SchemaParser;
import com.hackernews.graphql.config.AuthContext;
import com.hackernews.graphql.dataclasses.User;
import com.hackernews.graphql.endpoints.exceptions.SanitizedError;
import com.hackernews.graphql.repository.LinkRepository;
import com.hackernews.graphql.repository.UserRepository;
import com.hackernews.graphql.repository.VoteRepository;
import com.hackernews.graphql.resolver.LinkResolver;
import com.hackernews.graphql.resolver.Mutation;
import com.hackernews.graphql.resolver.Query;
import com.hackernews.graphql.resolver.SinginResolver;
import com.hackernews.graphql.resolver.VoteResolver;
import com.hackernews.graphql.scalars.Scalars;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	private static final LinkRepository linkRepository;
	private static final UserRepository userRepository;
	private static final VoteRepository voteRepository;	
	
	static {
		MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
		linkRepository = new LinkRepository(mongo.getCollection("links"));
		userRepository = new UserRepository(mongo.getCollection("users"));
		voteRepository = new VoteRepository(mongo.getCollection("votes"));
	}
	
	public GraphQLEndpoint() {
		super(buildSchema());
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser
				.newParser()
				.file("schema.graphqls")
				.resolvers(
						new Query(linkRepository), 
						new Mutation(linkRepository, userRepository, voteRepository), 
						new SinginResolver(),
						new LinkResolver(userRepository),
						new VoteResolver(linkRepository, userRepository))
				.scalars(Scalars.dateTime)
				.build()
				.makeExecutableSchema();
	}

	@Override
	protected GraphQLContext createContext(Optional<HttpServletRequest> request,
			Optional<HttpServletResponse> response) {
		User user = request.map(req -> req.getHeader("Authorization"))
				.filter(id -> !id.isEmpty())
				.map(id -> id.replace("Bearer ", ""))
				.map(userRepository::findById)
				.orElse(null);
		
		return new AuthContext(user, request, response);
	}

	@Override
	protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
		return errors.stream()
				.filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
				.map(e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
				.collect(Collectors.toList());
	}
	
	
}
