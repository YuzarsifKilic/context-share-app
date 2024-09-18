package com.yuzarsif.gameservice.client.epic.response;

import lombok.ToString;

import java.util.ArrayList;

@ToString
public class ProductResponse {

    public String __typename;
    public ArrayList<String> developers;
    public String id;
    public ArrayList<String> publishers;
    public String shortDescription;
    public SupportedModules supportedModules;
    public Tags tags;
    public String title;

    @ToString
    public static class Tags{
        public ArrayList<Object> accessibility;
        public ArrayList<Object> epicFeatures;
        public ArrayList<Object> events;
        public ArrayList<Feature> features;
        public ArrayList<Genre> genres;
        public ArrayList<Platform> platforms;
        public ArrayList<Object> usersSay;
    }

    @ToString
    public static class CriticReviews{
        public boolean openCritic;
    }

    @ToString
    public static class Genre{
        public String id;
        public String name;
    }

    @ToString
    public static class SupportedModules{
        public boolean addOns;
        public CriticReviews criticReviews;
        public boolean editions;
        public boolean experiences;
        public String mods;
    }

    @ToString
    public static class Platform{
        public String id;
        public String name;
    }

    @ToString
    public static class Feature{
        public String id;
        public String name;
    }
}
