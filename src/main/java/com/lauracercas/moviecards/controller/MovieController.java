package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
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
public class MovieController {

    private final MovieService movieService;

    private final String movieList = "movies/list";
    private final String movieForm = "movies/form";
    private final String title = "title";
    private final String objectMovie = "movie";

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movies")
    public String getMoviesList(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return movieList;
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute(objectMovie, new Movie());
        model.addAttribute(title, Messages.NEW_MOVIE_TITLE);
        return movieForm;
    }

    @PostMapping("saveMovie")
    public String saveMovie(@ModelAttribute Movie movie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return movieForm;
        }
        Movie movieSaved = movieService.save(movie);
        if (movieSaved.getId() != null) {
            model.addAttribute("message", Messages.UPDATED_MOVIE_SUCCESS);
        } else {
            model.addAttribute("message", Messages.SAVED_MOVIE_SUCCESS);
        }

        model.addAttribute(objectMovie, movieSaved);
        model.addAttribute(title, Messages.EDIT_MOVIE_TITLE);
        return movieForm;
    }

    @GetMapping("editMovie/{movieId}")
    public String editMovie(@PathVariable Integer movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        List<Actor> actors = movie.getActors();
        model.addAttribute(objectMovie, movie);
        model.addAttribute("actors", actors);

        model.addAttribute(title, Messages.EDIT_MOVIE_TITLE);

        return movieForm;
    }

}
