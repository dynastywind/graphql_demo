package com.lyndon.demo.graphql;

import com.lyndon.demo.entity.Actor;
import com.lyndon.demo.repository.ActorRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    private ActorRepository actorRepository;

    public Actor actorById(Long id) {
        return actorRepository.getOne(id);
    }

    public List<Actor> actors() {
        return actorRepository.findAll();
    }

}
