package com.lyndon.demo.graphql;

import com.lyndon.demo.entity.Actor;
import com.lyndon.demo.repository.ActorRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.Connection;
import graphql.relay.SimpleListConnection;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class Query implements GraphQLQueryResolver {

	@Autowired
	private ActorRepository actorRepository;

	public Actor actorById(Long id) {
		return actorRepository.getOne(id);
	}

	public Connection<Actor> actors(int page, int size, DataFetchingEnvironment env) {
		return new SimpleListConnection<>(actorRepository.findAll(PageRequest.of(page, size)).getContent()).get(env);
	}

}
