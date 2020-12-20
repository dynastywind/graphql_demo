package com.lyndon.demo.service;

import com.lyndon.demo.entity.Actor;
import com.lyndon.demo.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> list() {
        return actorRepository.findAll();
    }

    public Actor findOne(Long id) {
        return actorRepository.findById(id).orElse(null);
    }

}
