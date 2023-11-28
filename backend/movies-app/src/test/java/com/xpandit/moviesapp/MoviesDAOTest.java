package com.xpandit.moviesapp;

import com.xpandit.moviesapp.interfaces.IMoviesDAO;
import com.xpandit.moviesapp.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class MoviesDAOTest {

    @Autowired
    private IMoviesDAO moviesDAO;

    @Test
    public void testGetMovies() {
        final List<Movie> movies = moviesDAO.getMovies();

        Assert.isTrue(movies.size() > 0, "Test passed!");
    }
}
