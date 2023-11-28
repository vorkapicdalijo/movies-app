package com.xpandit.moviesapp.interfaces;

import com.xpandit.moviesapp.model.Movie;

import java.util.List;

public interface IMoviesService {

    List<Movie> getMovies();

    Movie getMovieById(Integer movieId);
}
