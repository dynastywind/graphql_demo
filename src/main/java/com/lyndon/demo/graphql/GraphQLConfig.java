package com.lyndon.demo.graphql;

import graphql.GraphQL;
import graphql.Scalars;
import graphql.execution.AsyncExecutionStrategy;
import graphql.kickstart.tools.SchemaParser;
import graphql.kickstart.tools.SchemaParserBuilder;
import graphql.kickstart.tools.SchemaParserOptions;
import graphql.kickstart.tools.relay.RelayConnectionFactory;
import graphql.schema.GraphQLScalarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphQLConfig {

	@Autowired
	private ActorDataFetcher actorDataFetcher;

	@Autowired
	private AsyncExecutionStrategy asyncExecutionStrategy;

	@Autowired
	@Qualifier("graphQLLocalDateTimeType")
	private GraphQLScalarType graphQLLocalDateTimeType;

	@Autowired
	@Qualifier("graphQLJSONType")
	private GraphQLScalarType graphQLJSONType;

	@Autowired
	private Query query;

	@Autowired
	private Mutation mutation;

	@Autowired
	private ActorResolver actorResolver;

	@Bean
	public GraphQL getGraphQL() throws Exception {
		SchemaParserBuilder builder = SchemaParser.newParser().file("schema.graphqls")
				.options(SchemaParserOptions.newOptions().typeDefinitionFactory(new RelayConnectionFactory())
						.typeDefinitionFactory(new AuditFactory()).includeUnusedTypes(true).build())
				.resolvers(query, mutation, actorResolver).dictionary("A", A.class)
				.scalars(Scalars.GraphQLLong, graphQLLocalDateTimeType, graphQLJSONType);
		SchemaParser parser = builder.build();
		return GraphQL.newGraphQL(parser.makeExecutableSchema()).queryExecutionStrategy(asyncExecutionStrategy).build();
	}

}
