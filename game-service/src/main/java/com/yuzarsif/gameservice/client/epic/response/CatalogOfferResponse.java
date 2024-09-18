package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;

@ToString
public class CatalogOfferResponse {

    public Data data;

    @ToString
    public static class AppliedRule{
        public String id;
        public Date endDate;
        public DiscountSetting discountSetting;
    }

    @ToString
    public static class Catalog{
        public CatalogOffer catalogOffer;
    }

    @ToString
    public static class CatalogOffer{
        public String title;
        public String id;
        public String namespace;
        public ArrayList<String> countriesBlacklist;
        public Object countriesWhitelist;
        public String developerDisplayName;
        public String description;
        public Date effectiveDate;
        public Object expiryDate;
        public Date viewableDate;
        public boolean allowPurchaseForPartialOwned;
        public String offerType;
        public Object externalLinks;
        public boolean isCodeRedemptionOnly;
        public ArrayList<KeyImage> keyImages;
        public Object longDescription;
        public Seller seller;
        public Object productSlug;
        public String publisherDisplayName;
        public Date releaseDate;
        public String urlSlug;
        public Object url;
        public ArrayList<Tag> tags;
        public ArrayList<Item> items;
        public ArrayList<CustomAttribute> customAttributes;
        public ArrayList<Category> categories;
        public ArrayList<OfferMapping> offerMappings;
        public Date pcReleaseDate;
        public Object prePurchase;
        public Object approximateReleasePlan;
        public Price price;
        public Object allDependNsOfferIds;
        public ArrayList<Object> majorNsOffers;
        public ArrayList<Object> subNsOffers;
        public String status;
        public String refundType;
    }

    @ToString
    public static class Category{
        public String path;
    }

    @ToString
    public static class CurrencyInfo{
        public int decimals;
    }

    @ToString
    public static class CustomAttribute{
        public String key;
        public String value;
    }

    @ToString
    public static class Data{
        @JsonProperty("Catalog")
        public Catalog catalog;
    }

    @ToString
    public static class DiscountSetting{
        public String discountType;
    }

    @ToString
    public static class Extensions{
    }

    @ToString
    public static class FmtPrice{
        public String originalPrice;
        public String discountPrice;
        public String intermediatePrice;
    }

    @ToString
    public static class Item{
        public String id;
        public String namespace;
        public ArrayList<Object> releaseInfo;
    }

    @ToString
    public static class KeyImage{
        public String type;
        public String url;
    }

    @ToString
    public static class LineOffer{
        public ArrayList<AppliedRule> appliedRules;
    }

    @ToString
    public static class OfferMapping {
        public Date createdDate;
        public Object deletedDate;
        public String pageSlug;
        public String pageType;
        public String productId;
        public String sandboxId;
        public Date updatedDate;
    }

    @ToString
    public static class Price{
        public TotalPrice totalPrice;
        public ArrayList<LineOffer> lineOffers;
        public PriceDetails priceDetails;
    }

    @ToString
    public static class PriceDetails{
        public ArrayList<Promotion> promotions;
    }

    @ToString
    public static class Promotion{
        public String type;
        public String promotionId;
        public Object membershipId;
        public int amount;
    }

    @ToString
    public static class Seller{
        public String id;
        public String name;
    }

    @ToString
    public static class Tag{
        public String id;
        public String name;
        public String groupName;
    }

    @ToString
    public static class TotalPrice{
        public int discountPrice;
        public int originalPrice;
        public int voucherDiscount;
        public int discount;
        public String currencyCode;
        public CurrencyInfo currencyInfo;
        public FmtPrice fmtPrice;
    }
}
