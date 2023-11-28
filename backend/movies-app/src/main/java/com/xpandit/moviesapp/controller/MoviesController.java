package com.xpandit.moviesapp.controller;

import com.xpandit.moviesapp.interfaces.IMoviesService;
import com.xpandit.moviesapp.model.Movie;
import com.xpandit.moviesapp.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("movies")
@RestController
public class MoviesController {

    private final IMoviesService moviesService;

    @Autowired
    public MoviesController(IMoviesService moviesService) {
        this.moviesService = moviesService;
    }

    // MOVIE FUNCTIONS - START

    @GetMapping
    public List<Movie> getMovies() {
        return moviesService.getMovies();
    }

    @GetMapping(path = "/{movieId}")
    public Movie getMovieDetailsById(
            @PathVariable("movieId") Integer movieId
    ) {
        return moviesService.getMovieDetailsById(movieId);
    }
    // MOVIE FUNCTIONS - END
}
