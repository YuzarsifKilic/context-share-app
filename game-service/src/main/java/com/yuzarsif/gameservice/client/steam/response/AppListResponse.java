package com.yuzarsif.gameservice.client.steam.response;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Data
public class AppListResponse {

    private AppList applist;

    @ToString
    @Data
    public static class AppList {
        private ArrayList<App> apps;
    }

    @ToString
    @Data
    public static class App {
        private Long appid;
        private String name;
    }
}
