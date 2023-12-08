package com.xpandit.moviesapp.interfaces;

import com.xpandit.moviesapp.model.Movie;
import com.xpandit.moviesapp.model.MoviePagination;

import java.util.List;

public interface IMoviesService {

    MoviePagination getMovies(MoviePagination paginationDto);

    Movie getMovieDetailsById(Integer movieId);
}
