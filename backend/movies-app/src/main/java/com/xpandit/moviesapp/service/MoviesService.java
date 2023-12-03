package com.xpandit.moviesapp.service;

import com.xpandit.moviesapp.interfaces.IMoviesDAO;
import com.xpandit.moviesapp.interfaces.IMoviesService;
import com.xpandit.moviesapp.model.Movie;
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
    public List<Movie> getMovies() {


        List<Movie> movies = this.moviesDAO.getMovies();

        movies.forEach(movie -> {
            try {
                byte[] fileContent = Files.readAllBytes(Paths.get(movie.getTitleImage().getFsPath()));

                movie.getTitleImage().setFileContent(fileContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return movies;
    }

    @Override
    public Movie getMovieDetailsById(Integer movieId) {
        return this.moviesDAO.getMovieDetailsById(movieId);
    }

    // MOVIE FUNCTIONS - END
}
