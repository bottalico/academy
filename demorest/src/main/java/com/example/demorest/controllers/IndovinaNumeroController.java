package com.example.demorest.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.demorest.model.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/in")
public class IndovinaNumeroController {
    private static final Logger log = LoggerFactory.getLogger(IndovinaNumeroController.class);

    // Elenco dei tentativi effettuati
    @GetMapping("/tentativi")
    public List<Result> getResults(@SessionAttribute(name = "results", required = false) List<Result> results) {
        if (results == null)
            results = new ArrayList<>();
        return results;
    }

    // Ricominciare il gioco dall'inizio
    @DeleteMapping("/tentativi")
    public void reset(HttpSession session) {
        log.info("Resetting game");
        session.removeAttribute("results");
        session.removeAttribute("number");
    }

    // Recupero del i-mo tentativo
    @GetMapping("/tentativi/{idx}")
    public ResponseEntity<Result> getResult(@SessionAttribute(required = false) List<Result> results,
            @PathVariable int idx) {
        if (results == null || results.size() <= idx) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(results.get(idx));
    }

    // Aggiunta di nuovo tentativo, avendo come risultato la risposta se è corretto
    // o meno (più alto(1) o più basso(-1))
    @PostMapping("/tentativi")
    public Result getResults(
            @SessionAttribute(required = false) List<Result> results,
            @SessionAttribute(required = false) Long number,
            HttpSession session,
            @RequestBody Result guess) {
        log.info("Guessing with {}", guess.getNumber());
        if (results == null) {
            results = new ArrayList<>();
            session.setAttribute("results", results);
        }
        if (number == null) {
            number = (long) ((Math.random() * 101) + 100);
            log.info("New number to guess: {}", number);
            session.setAttribute("number", number);
            results.clear();
        }
        Result result = new Result(guess.getNumber(), guess.getNumber().compareTo(number));
        results.add(result);
        return result;
    }

}
