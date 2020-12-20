package com.lyndon.demo.graphql;

import graphql.Scalars;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import static graphql.scalars.util.Kit.typeName;

@Configuration
public class GraphQLTypes {

	@Bean
	public GraphQLScalarType getGraphQLLongType() {
		return Scalars.GraphQLLong;
	}

	@Bean("graphQLLocalDateTimeType")
	public GraphQLScalarType getGraphQLLocalDateTimeType() {
		return GraphQLScalarType.newScalar().name("datetime").coercing(new Coercing<Date, Date>() {
			@Override
			public Date serialize(Object dataFetcherResult) throws CoercingSerializeException {
				if (dataFetcherResult instanceof Date) {
					return (Date) dataFetcherResult;
				}
				return null;
			}

			@Override
			public Date parseValue(Object input) throws CoercingParseValueException {
				try {
					if (input instanceof String) {
						return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String) input);
					}
				} catch (Exception e) {
					throw new CoercingParseLiteralException("Expected a 'String' but was '" + typeName(input) + "'.");
				}
				throw new CoercingParseLiteralException("Expected a 'String' but was '" + typeName(input) + "'.");
			}

			@Override
			public Date parseLiteral(Object input) throws CoercingParseLiteralException {
				try {
					if (input instanceof StringValue) {
						return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(((StringValue) input).getValue());
					}
				} catch (Exception e) {
					throw new CoercingParseLiteralException(
							"Expected AST type 'StringValue' but was '" + typeName(input) + "'.");
				}
				throw new CoercingParseLiteralException(
						"Expected AST type 'StringValue' but was '" + typeName(input) + "'.");
			}
		}).build();
	}

}
