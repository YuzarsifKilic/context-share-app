package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class Description {

    public Data data;

    @ToString
    public static class Configs{
        public String longDescription;
    }

    @ToString
    public static class Configuration{
        public Configs configs;
    }

    @ToString
    public static class Data{
        @JsonProperty("Product")
        public Product product;
    }

    @ToString
    public static class Product{
        public Sandbox sandbox;
    }

    @ToString
    public static class Sandbox{
        public ArrayList<Configuration> configuration;
    }

}
