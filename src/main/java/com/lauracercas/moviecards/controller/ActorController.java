package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.util.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Controller
public class ActorController {

    private final ActorService actorService;

    private final String actorList = "actors/list";
    private final String actorForm = "actors/form";
    private final String title = "title";
    private final String objectActor = "actor";

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("actors")
    public String getActorsList(Model model) {
        model.addAttribute("actors", actorService.getAllActors());
        return actorList;
    }

    @GetMapping("actors/new")
    public String newActor(Model model) {
        model.addAttribute(objectActor, new Actor());
        model.addAttribute(title, Messages.NEW_ACTOR_TITLE);
        return actorForm;
    }

    @PostMapping("saveActor")
    public String saveActor(@ModelAttribute Actor actor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return actorForm;
        }
        Actor actorSaved = actorService.save(actor);
        if (actor.getId() != null) {
            model.addAttribute("message", Messages.UPDATED_ACTOR_SUCCESS);
        } else {
            model.addAttribute("message", Messages.SAVED_ACTOR_SUCCESS);
        }

        model.addAttribute(objectActor, actorSaved);
        model.addAttribute(title, Messages.EDIT_ACTOR_TITLE);
        return actorForm;
    }

    @GetMapping("editActor/{actorId}")
    public String editActor(@PathVariable Integer actorId, Model model) {
        Actor actor = actorService.getActorById(actorId);
        List<Movie> movies = actor.getMovies();
        model.addAttribute(objectActor, actor);
        model.addAttribute("movies", movies);

        model.addAttribute(title, Messages.EDIT_ACTOR_TITLE);

        return actorForm;
    }

}
