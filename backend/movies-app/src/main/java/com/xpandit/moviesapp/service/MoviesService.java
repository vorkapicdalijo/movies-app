package com.xpandit.moviesapp.service;

import com.xpandit.moviesapp.interfaces.IMoviesDAO;
import com.xpandit.moviesapp.interfaces.IMoviesService;
import com.xpandit.moviesapp.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesService implements IMoviesService {

    private final Logger log = LogManager.getLogger(MoviesService.class);

    private IMoviesDAO moviesDAO;

    public void setMoviesDAO(IMoviesDAO moviesDAO) {
        this.moviesDAO = moviesDAO;
    }

    // MOVIE FUNCTIONS - START

    @Override
    public List<Movie> getMovies() {
        return this.moviesDAO.getMovies();
    }

    @Override
    public Movie getMovieDetailsById(Integer movieId) {
        return this.moviesDAO.getMovieDetailsById(movieId);
    }

    // MOVIE FUNCTIONS - END
}
