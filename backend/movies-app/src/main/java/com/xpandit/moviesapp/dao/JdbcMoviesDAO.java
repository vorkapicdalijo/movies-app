package com.xpandit.moviesapp.dao;

import com.xpandit.moviesapp.interfaces.IMoviesDAO;
import com.xpandit.moviesapp.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMoviesDAO implements IMoviesDAO {

    private final Logger log = LogManager.getLogger(JdbcMoviesDAO.class);


    // MOVIE DAO - START
    @Override
    public List<Movie> getMovies() {
        return null;
    }

    @Override
    public Movie getMovieById(Integer movieId) {
        return null;
    }

    // MOVIE DAO - END
}
