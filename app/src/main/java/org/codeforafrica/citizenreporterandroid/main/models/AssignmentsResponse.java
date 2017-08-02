package org.codeforafrica.citizenreporterandroid.main.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class AssignmentsResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Assignments> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page = page;
    }

    public List<Assignments> getResults(){
        return results;
    }

    public List<Assignments> getAssignments(){
        return results;
    }

    public void setResults(List<Assignments> results){
        this.results = results;
    }

    public int getTotalResults(){
        return totalResults;
    }

    public void setTotalResults(int totalResults){
        this.totalResults = totalResults;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public void setTotalPages(int totalPages){
        this.totalPages = totalPages;
    }

}

