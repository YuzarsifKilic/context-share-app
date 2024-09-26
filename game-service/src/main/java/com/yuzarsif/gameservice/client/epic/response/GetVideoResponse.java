package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GetVideoResponse {

    public Data data;


    public static class Data{
        @JsonProperty("Media")
        public Media media;
    }

    public static class GetMediaRef{
        public String accountId;
        public ArrayList<Output> outputs;
        public String namespace;
    }

    public static class Media{
        public GetMediaRef getMediaRef;
    }

    public static class Output{
        public int duration;
        public String url;
        public int width;
        public int height;
        public String key;
        public String contentType;
    }
}
