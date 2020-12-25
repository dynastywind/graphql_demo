package com.lyndon.demo.graphql;

import com.alibaba.fastjson.JSONObject;
import com.lyndon.demo.entity.Actor;
import com.lyndon.demo.repository.ActorRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public String test(AuditWrapper in) {
        TestInput input = JSONObject.parseObject(JSONObject.toJSONString(in.getContent()), TestInput.class);
        return JSONObject.toJSONString(input.getMessages());
    }

}
