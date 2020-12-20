package com.lyndon.demo.graphql;

import com.lyndon.demo.entity.Actor;
import com.lyndon.demo.repository.ActorRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActorDataFetcher {

    @Autowired
    private ActorRepository actorRepository;

    public DataFetcher<Actor> getActorById() {
        return env -> actorRepository.getOne(env.getArgument("id"));
    }

}
