package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class ProductConfigurationResponse {

    public Data data;

    @ToString
    public static class Configs{
        public String developerDisplayName;
        public ArrayList<KeyImage> keyImages;
        public String legalText;
        public Object pcReleaseDate;
        public String productDisplayName;
        public String privacyLink;
        public String publisherDisplayName;
        public String shortDescription;
        public ArrayList<SocialLink> socialLinks;
        public ArrayList<Object> supportedAudio;
        public ArrayList<String> supportedText;
        public ArrayList<Tag> tags;
        public TechnicalRequirements technicalRequirements;
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
    public static class KeyImage{
        public String type;
        public String url;
        public String alt;
    }

    @ToString
    public static class Product{
        public Sandbox sandbox;
    }

    @ToString
    public static class Sandbox{
        public ArrayList<Configuration> configuration;
    }

    @ToString
    public static class SocialLink{
        public String platform;
        public String url;
    }

    @ToString
    public static class Tag{
        public String id;
        public String name;
        public String groupName;
    }

    @ToString
    public static class TechnicalRequirements{
        public Object macos;
        public ArrayList<Window> windows;
    }

    @ToString
    public static class Window{
        public String minimum;
        public String recommended;
        public String title;
    }

}
