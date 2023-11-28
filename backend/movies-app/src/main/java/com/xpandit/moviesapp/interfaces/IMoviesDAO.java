package com.xpandit.moviesapp.interfaces;

import com.xpandit.moviesapp.model.Movie;

import java.util.List;

public interface IMoviesDAO {

    List<Movie> getMovies();

    Movie getMovieDetailsById(Integer movieId);
}
