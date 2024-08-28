package com.yuzarsif.gameservice.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
public class AppDetailsResponse {

    private Boolean success;
    private AppData data;

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AppData {
        private String type;
        private String name;
        private int steam_appid;
        private boolean is_free;
        private String detailed_description;
        private String about_the_game;
        private String short_description;
        private String supported_languages;
        private String header_image;
        private String capsule_image;
        private String capsule_imagev5;
        private Object website;
        private PcRequirements pc_requirements;
        private String legal_notice;
        private ArrayList<String> developers;
        private ArrayList<String> publishers;
        private PriceOverview price_overview;
        private ArrayList<Integer> packages;
        private ArrayList<PackageGroup> package_groups;
        private Platforms platforms;
        private ArrayList<Category> categories;
        private ArrayList<Genre> genres;
        private ArrayList<Screenshot> screenshots;
        private ArrayList<Movie> movies;
        private Recommendations recommendations;
        private Achievements achievements;
        private ReleaseDate release_date;
        private SupportInfo support_info;
        private String background;
        private String background_raw;
        private ContentDescriptors content_descriptors;
        private Ratings ratings;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Achievements{
        public int total;
        public ArrayList<Highlighted> highlighted;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Category{
        public int id;
        public String description;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Cero{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentDescriptors{
        public ArrayList<Object> ids;
        public Object notes;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Crl{
        public String rating;
        public String use_age_gate;}

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Csrr{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dejus{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Esrb{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Genre{
        public String id;
        public String description;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Highlighted{
        public String name;
        public String path;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Kgrb{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LinuxRequirements{
        public String minimum;
        public String recommended;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MacRequirements{
        public String minimum;
        public String recommended;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Movie{
        public int id;
        public String name;
        public String thumbnail;
        public Webm webm;
        public Mp4 mp4;
        public boolean highlight;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Mp4{
        @JsonProperty("480")
        public String _480;
        public String max;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Nzoflc{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Oflc{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PackageGroup{
        public String name;
        public String title;
        public String description;
        public String selection_text;
        public String save_text;
        public int display_type;
        public String is_recurring_subscription;
        public ArrayList<Sub> subs;
    }

    @Data
    @ToString
    public static class PcRequirements{
        public String minimum;
        public String recommended;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pegi{
        public String rating;
        public String descriptors;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Platforms{
        public boolean windows;
        public boolean mac;
        public boolean linux;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PriceOverview{
        public String currency;
        public int initial;
        @JsonProperty("final")
        public int myfinal;
        public int discount_percent;
        public String initial_formatted;
        public String final_formatted;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Ratings{
        public Esrb esrb;
        public Oflc oflc;
        public Nzoflc nzoflc;
        public Cero cero;
        public Dejus dejus;
        public Pegi pegi;
        public Crl crl;
        public Csrr csrr;
        public Usk usk;
        public Kgrb kgrb;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Recommendations{
        public int total;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReleaseDate{
        public boolean coming_soon;
        public String date;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Screenshot{
        public int id;
        public String path_thumbnail;
        public String path_full;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sub{
        public int packageid;
        public String percent_savings_text;
        public int percent_savings;
        public String option_text;
        public String option_description;
        public String can_get_free_license;
        public boolean is_free_license;
        public int price_in_cents_with_discount;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SupportInfo{
        public String url;
        public String email;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Usk{
        public String rating;
    }

    @Data
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Webm{
        @JsonProperty("480")
        public String _480;
        public String max;
    }
}
