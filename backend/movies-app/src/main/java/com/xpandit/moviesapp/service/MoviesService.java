package com.xpandit.moviesapp.service;

import com.xpandit.moviesapp.interfaces.IMoviesDAO;
import com.xpandit.moviesapp.interfaces.IMoviesService;
import com.xpandit.moviesapp.model.Movie;
import com.xpandit.moviesapp.model.MoviePagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MoviesService implements IMoviesService {

    private final Logger log = LogManager.getLogger(MoviesService.class);

    private IMoviesDAO moviesDAO;

    @Autowired
    public void setMoviesDAO(IMoviesDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    // MOVIE FUNCTIONS - START

    @Override
    public MoviePagination getMovies(MoviePagination paginationDto) {
        paginationDto.setLimit(10);

        return this.moviesDAO.getMovies(paginationDto);
    }

    @Override
    public Movie getMovieDetailsById(Integer movieId) {

        Movie movie =  this.moviesDAO.getMovieDetailsById(movieId);

        return movie;
    }

    // MOVIE FUNCTIONS - END
}
