package com.xpandit.moviesapp.dao;

import com.xpandit.moviesapp.interfaces.IMoviesDAO;
import com.xpandit.moviesapp.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMoviesDAO implements IMoviesDAO {

    private final Logger log = LogManager.getLogger(JdbcMoviesDAO.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMoviesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // MOVIE DAO - START
    @Override
    public List<Movie> getMovies() {
        try {
            final StringBuilder queryBuilder = new StringBuilder()
                    .append(" SELECT ")
                    .append("     m.id, ")
                    .append("     m.title, ")
                    .append("     m.description, ")
                    .append("     m.duration, ")
                    .append("     m.release_date, ")
                    .append("     m.revenue, ")
                    .append("     mt.type_id, ")
                    .append("     mt.type_name ")
                    .append(" FROM ")
                    .append("     movies m ")
                    .append(" LEFT JOIN ")
                    .append("     movie_types mt ")
                    .append(" ON ")
                    .append("     m.movie_type_id = mt.type_id ");

            List<Movie> movies = jdbcTemplate.query(queryBuilder.toString(), (resultSet, i) -> {
                Movie movie = new Movie();

                movie.setId(Integer.parseInt(resultSet.getString("id")));
                movie.setMovieTypeName(resultSet.getString("type_name"));
                movie.setMovieTypeId(Integer.parseInt(resultSet.getString("type_id")));
                movie.setTitle(resultSet.getString("title"));
                movie.setDescription(resultSet.getString("description"));
                movie.setReleaseDate(resultSet.getDate("release_date"));
                movie.setDuration(resultSet.getTime("duration"));
                movie.setRevenue(Float.parseFloat(resultSet.getString("revenue")));

                return movie;
            });
            return movies;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        catch (Exception e) {
            log.error("Error while fetching movies: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Movie getMovieDetailsById(Integer movieId) {
        if (movieId == null) {
            throw new IllegalArgumentException("Missing movie ID parameter!");
        }
        try {
            final StringBuilder queryBuilder = new StringBuilder()
                    .append(" SELECT ")
                    .append("     m.id, ")
                    .append("     m.title, ")
                    .append("     m.description, ")
                    .append("     m.duration, ")
                    .append("     m.release_date, ")
                    .append("     m.revenue, ")
                    .append("     mt.type_id, ")
                    .append("     mt.type_name ")
                    .append(" FROM ")
                    .append("     movies m ")
                    .append(" LEFT JOIN ")
                    .append("     movie_types mt ")
                    .append(" ON ")
                    .append("     m.movie_type_id = mt.type_id ")
                    .append(" WHERE ")
                    .append("     m.id = ? ");

            Movie selectedMovie = jdbcTemplate
                    .queryForObject(
                            queryBuilder.toString(),
                            new Object[]{movieId},
                            (resultSet, i) -> {
                                Movie movie = new Movie();

                                movie.setId(Integer.parseInt(resultSet.getString("id")));
                                movie.setMovieTypeName(resultSet.getString("type_name"));
                                movie.setMovieTypeId(Integer.parseInt(resultSet.getString("type_id")));
                                movie.setTitle(resultSet.getString("title"));
                                movie.setDescription(resultSet.getString("description"));
                                movie.setReleaseDate(resultSet.getDate("release_date"));
                                movie.setDuration(resultSet.getTime("duration"));
                                movie.setRevenue(Float.parseFloat(resultSet.getString("revenue")));

                                return movie;
                            });
            return selectedMovie;
        } catch (Exception e) {
            log.error("Error while fetching movie details: {}", e.getMessage());
            return null;
        }
    }

    // MOVIE DAO - END
}
