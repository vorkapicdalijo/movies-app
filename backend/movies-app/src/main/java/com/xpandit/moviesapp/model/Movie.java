package com.xpandit.moviesapp.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Movie {
    private Integer id;

    private Integer movieTypeId;

    private String movieTypeName;

    private String title;

    private String description;

    private Date duration;

    private Date releaseDate;

    private Float revenue;

    private Date createDate;

    private MovieImage titleImage;

    private List<MovieImage> detailImages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMovieTypeId() {
        return movieTypeId;
    }

    public void setMovieTypeId(Integer movieTypeId) {
        this.movieTypeId = movieTypeId;
    }

    public String getMovieTypeName() {
        return movieTypeName;
    }

    public void setMovieTypeName(String movieTypeName) {
        this.movieTypeName = movieTypeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Float getRevenue() {
        return revenue;
    }

    public void setRevenue(Float revenue) {
        this.revenue = revenue;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public MovieImage getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(MovieImage titleImage) {
        this.titleImage = titleImage;
    }

    public List<MovieImage> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<MovieImage> detailImages) {
        this.detailImages = detailImages;
    }
}
