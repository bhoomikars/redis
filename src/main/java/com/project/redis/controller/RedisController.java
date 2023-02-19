package com.project.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis/Api/")
@Slf4j
public class RedisController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/getHelloWorld")
    public String helloWorldController() {
        return "hello-world";
    }


    @PutMapping("/updatePilotClub/{clubId}/{win}")
    public ResponseEntity<String> updatePilotClub(@PathVariable String clubId, @PathVariable String win) {
        log.info("update the pilot club");
        try {
            redisTemplate.opsForValue().set("KAFKA:CLUBS:value", clubId);
            redisTemplate.opsForSet().add("kafka:set", clubId);
            redisTemplate.opsForHash().put(clubId, win, "true");
        } catch (Exception e) {
            log.error("exception occurred while updating the value");
            throw e;
        }
        log.info("updated the kafka club");
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/getPilotValue")
    public String getKafkaPilotClubValue() {
        return redisTemplate.opsForValue().get("Pilot");
    }


}
