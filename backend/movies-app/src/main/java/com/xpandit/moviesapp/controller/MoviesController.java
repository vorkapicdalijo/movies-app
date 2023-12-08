package com.xpandit.moviesapp.controller;

import com.xpandit.moviesapp.interfaces.IMoviesService;
import com.xpandit.moviesapp.model.Movie;
import com.xpandit.moviesapp.model.MoviePagination;
import com.xpandit.moviesapp.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public @ResponseBody MoviePagination getMovies(
            @RequestBody(required = false) MoviePagination paginationDto
    ) {

        return moviesService.getMovies(paginationDto);
    }

    @GetMapping(path = "/{movieId}")
    public Movie getMovieDetailsById(
            @PathVariable("movieId") Integer movieId
    ) {
        return moviesService.getMovieDetailsById(movieId);
    }
    // MOVIE FUNCTIONS - END
}
