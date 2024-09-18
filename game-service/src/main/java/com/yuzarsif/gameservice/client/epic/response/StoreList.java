package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;

@ToString
public class StoreList {

    public Data data;

    @ToString
    public static class AppliedRule {
        public String id;
        public Date endDate;
        public DiscountSetting discountSetting;
    }

    @ToString
    public static class ApproximateReleasePlan {
        public int day;
        public int month;
        public Object quarter;
        public int year;
        public String releaseDateType;
    }

    @ToString
    public static class Catalog {
        public SearchStore searchStore;
    }

    @ToString
    public static class CatalogNs {
        public ArrayList<Mapping> mappings;
    }

    @ToString
    public static class Category {
        public String path;
    }

    @ToString
    public static class CurrencyInfo {
        public int decimals;
    }

    @ToString
    public static class CustomAttribute {
        public String key;
        public String value;
    }

    @ToString
    public static class Data {
        @JsonProperty("Catalog")
        public Catalog catalog;
    }

    @ToString
    public static class DiscountSetting {
        public String discountType;
    }

    @ToString
    public static class Element {
        public String title;
        public String id;
        public String namespace;
        public String description;
        public Date effectiveDate;
        public boolean isCodeRedemptionOnly;
        public ArrayList<KeyImage> keyImages;
        public int currentPrice;
        public Seller seller;
        public String productSlug;
        public String urlSlug;
        public Object url;
        public ArrayList<Tag> tags;
        public ArrayList<Item> items;
        public ArrayList<CustomAttribute> customAttributes;
        public ArrayList<Category> categories;
        public CatalogNs catalogNs;
        public ArrayList<OfferMapping> offerMappings;
        public String developerDisplayName;
        public String publisherDisplayName;
        public Price price;
        public boolean prePurchase;
        public String releaseDate;
        public Date pcReleaseDate;
        public Date viewableDate;
        public ApproximateReleasePlan approximateReleasePlan;
    }

    @ToString
    public static class FmtPrice {
        public String originalPrice;
        public String discountPrice;
        public String intermediatePrice;
    }

    @ToString
    public static class Item {
        public String id;
        public String namespace;
    }

    @ToString
    public static class KeyImage {
        public String type;
        public String url;
    }

    @ToString
    public static class LineOffer {
        public ArrayList<AppliedRule> appliedRules;
    }

    @ToString
    public static class Mapping {
        public String pageSlug;
        public String pageType;
    }

    @ToString
    public static class OfferMapping {
        public String pageSlug;
        public String pageType;
    }

    @ToString
    public static class Paging {
        public int count;
        public int total;
    }

    @ToString
    public static class Price {
        public TotalPrice totalPrice;
        public ArrayList<LineOffer> lineOffers;
    }

    @ToString
    public static class SearchStore {
        public ArrayList<Element> elements;
        public Paging paging;
    }

    @ToString
    public static class Seller {
        public String id;
        public String name;
    }

    @ToString
    public static class Tag {
        public String id;
    }

    @ToString
    public static class TotalPrice {
        public int discountPrice;
        public int originalPrice;
        public int voucherDiscount;
        public int discount;
        public String currencyCode;
        public CurrencyInfo currencyInfo;
        public FmtPrice fmtPrice;
    }
}
