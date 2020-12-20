package com.lyndon.demo.graphql;

import com.lyndon.demo.entity.Actor;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.execution.AsyncExecutionStrategy;
import graphql.kickstart.tools.SchemaParser;
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
	private Query query;

	@Autowired
	private Mutation mutation;

	@Autowired
	private ActorResolver actorResolver;

	@Bean
	public GraphQL getGraphQL() throws Exception {
		return GraphQL
				.newGraphQL(SchemaParser.newParser().file("schema.graphqls")
						.options(SchemaParserOptions.newOptions().typeDefinitionFactory(new RelayConnectionFactory())
								.build())
						.resolvers(query, mutation, actorResolver)
						.scalars(Scalars.GraphQLLong, graphQLLocalDateTimeType).build().makeExecutableSchema())
				.queryExecutionStrategy(asyncExecutionStrategy).build();
	}

}
