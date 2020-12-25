package com.lyndon.demo.graphql;

import com.alibaba.fastjson.JSONObject;
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

	@Bean("graphQLJSONType")
	public GraphQLScalarType getGraphQLJSONType() {
		return GraphQLScalarType.newScalar().name("JSONType").coercing(new Coercing<JSONObject, JSONObject>() {
			@Override
			public JSONObject serialize(Object dataFetcherResult) throws CoercingSerializeException {
				if (dataFetcherResult instanceof String) {
					return JSONObject.parseObject((String) dataFetcherResult);
				}
				throw new CoercingParseLiteralException(
						"Expected a 'String' but was '" + typeName(dataFetcherResult) + "'.");
			}

			@Override
			public JSONObject parseValue(Object input) throws CoercingParseValueException {
				if (input instanceof String) {
					return JSONObject.parseObject((String) input);
				}
				throw new CoercingParseLiteralException("Expected a 'String' but was '" + typeName(input) + "'.");
			}

			@Override
			public JSONObject parseLiteral(Object input) throws CoercingParseLiteralException {
				if (input instanceof StringValue) {
					return JSONObject.parseObject(((StringValue) input).getValue());
				}
				throw new CoercingParseLiteralException(
						"Expected AST type 'StringValue' but was '" + typeName(input) + "'.");
			}
		}).build();
	}

}
