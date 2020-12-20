package com.lyndon.demo.graphql;

import com.lyndon.demo.entity.Actor;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.stereotype.Component;

@Component
public class ActorResolver implements GraphQLResolver<Actor> {
}
