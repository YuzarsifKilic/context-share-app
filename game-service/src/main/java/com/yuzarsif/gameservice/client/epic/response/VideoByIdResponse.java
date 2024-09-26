package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class VideoByIdResponse {

    public Data data;

    public static class Data{
        @JsonProperty("Video")
        public Video video;
    }


    public static class FetchVideoByLocale{
        public String recipe;
        public String mediaRefId;
    }

    public static class Video{
        public ArrayList<FetchVideoByLocale> fetchVideoByLocale;
    }
}