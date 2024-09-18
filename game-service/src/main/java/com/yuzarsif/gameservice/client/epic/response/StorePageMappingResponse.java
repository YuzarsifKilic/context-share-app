package com.yuzarsif.gameservice.client.epic.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.Date;

@ToString
public class StorePageMappingResponse {

    public Data data;
    @ToString
    public static class Data{
        @JsonProperty("StorePageMapping")
        public StorePageMapping storePageMapping;
    }

    @ToString
    public static class Mapping{
        public String pageSlug;
        public String pageType;
        public String sandboxId;
        public String productId;
        public Date createdDate;
        public Date updatedDate;
        public Mappings mappings;
    }

    @ToString
    public static class Mappings{
        public Object cmsSlug;
        public String offerId;
        public Offer offer;
        public Object prePurchaseOfferId;
        public Object prePurchaseOffer;
        public Object pageId;
    }

    @ToString
    public static class Offer{
        public String id;
        public String namespace;
        public Date effectiveDate;
        public Object expiryDate;
    }

    @ToString
    public static class StorePageMapping{
        public Mapping mapping;
    }
}
