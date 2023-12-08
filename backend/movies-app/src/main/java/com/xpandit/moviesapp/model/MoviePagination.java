package com.xpandit.moviesapp.model;

import java.util.Date;
import java.util.List;

public class MoviePagination {

    private int offset = 0;
    private int limit = 25;
    private int totalLength;
    private List<Movie> data;

    //Filters
    private boolean getTop10MoviesByRevenue;
    private boolean getTop10MoviesByRevenueByYear;
    private Date filterYear;

    public Date getFilterYear() {
        return filterYear;
    }

    public void setFilterYear(Date filterYear) {
        this.filterYear = filterYear;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public List<Movie> getData() {
        return data;
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    public boolean isGetTop10MoviesByRevenue() {
        return getTop10MoviesByRevenue;
    }

    public void setGetTop10MoviesByRevenue(boolean getTop10MoviesByRevenue) {
        this.getTop10MoviesByRevenue = getTop10MoviesByRevenue;
    }

    public boolean isGetTop10MoviesByRevenueByYear() {
        return getTop10MoviesByRevenueByYear;
    }

    public void setGetTop10MoviesByRevenueByYear(boolean getTop10MoviesByRevenueByYear) {
        this.getTop10MoviesByRevenueByYear = getTop10MoviesByRevenueByYear;
    }
}
