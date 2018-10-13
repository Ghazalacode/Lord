package com.example.hossam.lord.StatsActivity.Data.Model;

import java.util.ArrayList;

public class ActivitiesResponseMain  {

    ArrayList<ActivitiesResponse> responses;

    public ActivitiesResponseMain(ArrayList<ActivitiesResponse> responses) {
        this.responses = responses;
    }

    public ArrayList<ActivitiesResponse> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<ActivitiesResponse> responses) {
        this.responses = responses;
    }
}
