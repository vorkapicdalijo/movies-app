package com.xpandit.moviesapp.dao;

import com.xpandit.moviesapp.interfaces.IMoviesDAO;
import com.xpandit.moviesapp.model.Movie;
import com.xpandit.moviesapp.model.MovieImage;
import com.xpandit.moviesapp.model.MoviePagination;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcMoviesDAO implements IMoviesDAO {

    protected static final String SELECT_START_WRAPPER_FOR_PAGGING = " SELECT * FROM ( SELECT ROW_NUMBER() OVER () AS RNUM, COUNT(*) OVER () AS RESULT_COUNT, query.id, query.title, query.description, query.duration, query.release_date, query.revenue, query.type_id, query.type_name, query.image_id, query.file_name, query.fs_path, query.image_type_id, query.image_type_name FROM ( ";

    protected static final String SELECT_END_WRAPPER_FOR_PAGGING = "  ) AS query) AS result WHERE RNUM between (:offset + 1)  and (:offset + :limit) ";

    private final Logger log = LogManager.getLogger(JdbcMoviesDAO.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcMoviesDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // MOVIE DAO - START
    @Override
    public MoviePagination getMovies(MoviePagination paginationDto) {
        try {
            final StringBuilder queryBuilder = new StringBuilder(SELECT_START_WRAPPER_FOR_PAGGING)
                    .append(" SELECT ")
                    .append("     m.id, ")
                    .append("     m.title, ")
                    .append("     m.description, ")
                    .append("     m.duration, ")
                    .append("     m.release_date, ")
                    .append("     m.revenue, ")
                    .append("     mt.type_id, ")
                    .append("     mt.type_name, ")
                    .append("     mi.id as image_id, ")
                    .append("     mi.file_name, ")
                    .append("     mi.fs_path, ")
                    .append("     it.type_id as image_type_id, ")
                    .append("     it.type_name as image_type_name ")
                    .append(" FROM ")
                    .append("     movies m ")
                    .append(" LEFT JOIN ")
                    .append("     movie_types mt ")
                    .append(" ON ")
                    .append("     m.movie_type_id = mt.type_id ")
                    .append(" LEFT JOIN ")
                    .append("     movie_images mi ")
                    .append(" ON ")
                    .append("     m.id = mi.movie_id ")
                    .append(" LEFT JOIN ")
                    .append("     image_types it ")
                    .append(" ON ")
                    .append("     mi.image_type_id = it.type_id ");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("offset", paginationDto.getOffset() * paginationDto.getLimit());
            params.put("limit", paginationDto.getLimit());

            if (paginationDto.isGetTop10MoviesByRevenueByYear()) {
                if (paginationDto.getFilterYear() != null) {
                    queryBuilder.append(" WHERE EXTRACT(YEAR FROM release_date) = :filterYear  ");
                    params.put("filterYear", paginationDto.getFilterYear());
                }
            }

            if (paginationDto.isGetTop10MoviesByRevenue()) {
                queryBuilder.append(" ORDER BY revenue DESC LIMIT 10 ");
            }

            queryBuilder.append(SELECT_END_WRAPPER_FOR_PAGGING);

            MoviePagination moviePagination = (MoviePagination) namedParameterJdbcTemplate.query(queryBuilder.toString(), new MapSqlParameterSource(params), new MoviesPaginationExtractor());
            return moviePagination;

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        catch (Exception e) {
            log.error("Error while fetching movies: {}", e.getMessage());
            return null;
        }
    }

    private final class MoviesPaginationExtractor implements ResultSetExtractor {

        @Override
        public Object extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Movie> movies = new ArrayList<>();
            final MoviePagination pagination = new MoviePagination();
            int totalLength = 0;

            while (resultSet.next()) {

                if (totalLength == 0) {
                    totalLength = resultSet.getInt("result_count");
                }

                Movie movie = new Movie();

                movie.setId(resultSet.getInt("id"));
                movie.setMovieTypeName(resultSet.getString("type_name"));
                movie.setMovieTypeId(resultSet.getInt("type_id"));
                movie.setTitle(resultSet.getString("title"));
                movie.setDescription(resultSet.getString("description"));
                movie.setReleaseDate(resultSet.getDate("release_date"));
                movie.setDuration(resultSet.getTime("duration"));
                movie.setRevenue(resultSet.getFloat("revenue"));

                MovieImage titleImage = new MovieImage();

                titleImage.setId(resultSet.getInt("image_id"));
                titleImage.setImageTypeId(resultSet.getInt("image_type_id"));
                titleImage.setImageName(resultSet.getString("file_name"));
                titleImage.setImagePath(resultSet.getString("fs_path"));

                movie.setTitleImage(titleImage);

                movies.add(movie);
            }

            pagination.setTotalLength(totalLength);
            pagination.setData(movies);
            pagination.setLimit(10);

            return pagination;
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
                    .append("     mt.type_name, ")
                    .append("     mi.id as image_id, ")
                    .append("     mi.file_name, ")
                    .append("     mi.fs_path, ")
                    .append("     it.type_id as image_type_id, ")
                    .append("     it.type_name as image_type_name ")
                    .append(" FROM ")
                    .append("     movies m ")
                    .append(" LEFT JOIN ")
                    .append("     movie_types mt ")
                    .append(" ON ")
                    .append("     m.movie_type_id = mt.type_id ")
                    .append(" LEFT JOIN ")
                    .append("     movie_images mi ")
                    .append(" ON ")
                    .append("     m.id = mi.movie_id ")
                    .append(" LEFT JOIN ")
                    .append("     image_types it ")
                    .append(" ON ")
                    .append("     mi.image_type_id = it.type_id ")
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

                                MovieImage titleImage = new MovieImage();

                                titleImage.setId(Integer.parseInt(resultSet.getString("image_id")));
                                titleImage.setImageTypeId(Integer.parseInt(resultSet.getString("image_type_id")));
                                titleImage.setImageName(resultSet.getString("file_name"));
                                titleImage.setImagePath(resultSet.getString("fs_path"));

                                movie.setTitleImage(titleImage);

                                // TODO: Postaviti prave slike - ovo je samo za test
                                List<MovieImage> movieImages = new ArrayList<>(Collections.nCopies(8, titleImage));

                                movie.setDetailImages(movieImages);
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
