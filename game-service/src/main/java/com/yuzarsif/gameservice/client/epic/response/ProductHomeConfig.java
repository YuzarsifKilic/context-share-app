package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class ProductHomeConfig {

    public Data data;

    public static class Configs{
        public ArrayList<KeyImage> keyImages;
        public String longDescription;
    }

    public static class Configuration{
        public Configs configs;
    }

    public static class Data{
        @JsonProperty("Product")
        public Product product;
    }

    public static class KeyImage{
        public String type;
        public String url;
        public String alt;
    }

    public static class Product{
        public Sandbox sandbox;
    }

    public static class Sandbox{
        public ArrayList<Configuration> configuration;
    }
}