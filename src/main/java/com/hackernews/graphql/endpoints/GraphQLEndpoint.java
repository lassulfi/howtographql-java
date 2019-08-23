package com.hackernews.graphql.endpoints;

import javax.servlet.annotation.WebServlet;

import com.coxautodev.graphql.tools.SchemaParser;
import com.hackernews.graphql.repository.LinkRepository;
import com.hackernews.graphql.resolver.Query;

import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

	public GraphQLEndpoint() {
		super(buildSchema());
	}

	private static GraphQLSchema buildSchema() {
		LinkRepository linkRepository = new LinkRepository();

		return SchemaParser
				.newParser()
				.file("schema.graphqls")
				.resolvers(new Query(linkRepository))
				.build()
				.makeExecutableSchema();
	}
}
