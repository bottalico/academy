package com.example.demorest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();
    List<Greeting> history = new ArrayList<>();

    @GetMapping("/greeting")
    public List<Greeting> history() {
        return this.history;
    }

    @GetMapping("/greeting/{name}/{cognome}")
    public ResponseEntity<Greeting> greet(@PathVariable(value = "name", required = false) String name,
            @PathVariable("cognome") String cognome) {
        if (name == null) {
            name = "World";
        }
        Greeting greeting = new Greeting("Hello %s (%d)", cognome + " " + name, counter.incrementAndGet());
        this.history.add(greeting);
        return ResponseEntity.ok(greeting);
    }

    @PostMapping("/greeting/{name}/{cognome}")
    public Greeting update(@RequestBody Greeting greeting) {
        this.history.add(greeting);
        return greeting;
    }
}

class Greeting {
    private String template;
    private String name;
    private long counter;

    public Greeting(String template, String name, long counter) {
        this.template = template;
        this.name = name;
        this.counter = counter;
    }

    public String getGreeting() {
        return String.format(template, name, counter);
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCounter() {
        return this.counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }
}
