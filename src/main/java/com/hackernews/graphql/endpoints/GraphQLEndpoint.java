package com.hackernews.graphql.endpoints;

import javax.servlet.annotation.WebServlet;

import com.coxautodev.graphql.tools.SchemaParser;
import com.hackernews.graphql.repository.LinkRepository;
import com.hackernews.graphql.resolver.Mutation;
import com.hackernews.graphql.resolver.Query;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	private static final LinkRepository linkRepository;
	
	static {
		MongoDatabase mongo = new MongoClient().getDatabase("hackernews");
		linkRepository = new LinkRepository(mongo.getCollection("links"));
	}
	
	public GraphQLEndpoint() {
		super(buildSchema());
	}

	private static GraphQLSchema buildSchema() {
		return SchemaParser
				.newParser()
				.file("schema.graphqls")
				.resolvers(new Query(linkRepository), new Mutation(linkRepository))
				.build()
				.makeExecutableSchema();
	}
}
