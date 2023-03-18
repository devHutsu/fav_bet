package dev.hutsu.bet_app.basketball.service;

import dev.hutsu.bet_app.basketball.model.EventBasketball;
//import dev.hutsu.bet_app.basketball.repo.EventBasketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
//@Transactional
//public class EventBasketService {
//
//    private final EventBasketRepo repo;
//
//    @Autowired
//    public EventBasketService(EventBasketRepo eventBasketRepo) {
//        this.repo = eventBasketRepo;
//    }
//
//
//    public EventBasketball save(EventBasketball basketball){
//        EventBasketball result = repo.findByUrl(basketball.getUrl());
//
//        if (result == null){
//            result = repo.save(basketball);
//        }
//        return result;
//    }
//
//
//}
