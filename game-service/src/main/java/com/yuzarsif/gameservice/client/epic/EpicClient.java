package com.yuzarsif.gameservice.client.epic;

import com.yuzarsif.gameservice.client.epic.response.*;
import com.yuzarsif.gameservice.client.steam.response.AppListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class EpicClient {

    private final RestTemplate restTemplate;

    public StoreList getStoreList(Integer total, Integer index) {
        String url = "https://store.epicgames.com/graphql";

        // GraphQL sorgusu
        String query = "query searchStoreQuery($allowCountries: String!, $category: String!, $comingSoon: Boolean!, $count: Int!, $country: String!, $keywords: String!, $locale: String!, $sortBy: String!, $sortDir: String!, $start: Int!, $tag: String!) {" +
                "  Catalog {" +
                "    searchStore(" +
                "      allowCountries: $allowCountries," +
                "      category: $category," +
                "      comingSoon: $comingSoon," +
                "      count: $count," +
                "      country: $country," +
                "      keywords: $keywords," +
                "      locale: $locale," +
                "      sortBy: $sortBy," +
                "      sortDir: $sortDir," +
                "      start: $start," +
                "      tag: $tag" +
                "    ) {" +
                "      elements {" +
                "        title" +
                "        id" +
                "        namespace" +
                "        description" +
                "        effectiveDate" +
                "        isCodeRedemptionOnly" +
                "        keyImages {" +
                "          type" +
                "          url" +
                "        }" +
                "        currentPrice" +
                "        seller {" +
                "          id" +
                "          name" +
                "        }" +
                "        productSlug" +
                "        urlSlug" +
                "        url" +
                "        tags {" +
                "          id" +
                "        }" +
                "        items {" +
                "          id" +
                "          namespace" +
                "        }" +
                "        customAttributes {" +
                "          key" +
                "          value" +
                "        }" +
                "        categories {" +
                "          path" +
                "        }" +
                "        catalogNs {" +
                "          mappings {" +
                "            pageSlug" +
                "            pageType" +
                "          }" +
                "        }" +
                "        offerMappings {" +
                "          pageSlug" +
                "          pageType" +
                "        }" +
                "        developerDisplayName" +
                "        publisherDisplayName" +
                "        releaseDate" +
                "        pcReleaseDate" +
                "        viewableDate" +
                "        approximateReleasePlan {" +
                "          releaseDateType" +
                "        }" +
                "      }" +
                "      paging {" +
                "        count" +
                "        total" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        // Değişkenler
        Map<String, Object> variables = new HashMap<>();
        variables.put("allowCountries", "TR");
        variables.put("category", "games/edition/base");
        variables.put("comingSoon", false);
        variables.put("count", total);
        variables.put("country", "TR");
        variables.put("keywords", "");
        variables.put("locale", "en-US");
        variables.put("sortBy", "releaseDate");
        variables.put("sortDir", "DESC");
        variables.put("start", index);
        variables.put("tag", "");

        // İstek gövdesini oluştur
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıkları oluştur
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteği gönder
            ResponseEntity<StoreList> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    StoreList.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Status code: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public StorePageMappingResponse getStorePageMapping(String pageSlug, String locale) {
        String url = "https://store.epicgames.com/graphql";

        // GraphQL sorgusu
        String query = "query getMappingByPageSlug($pageSlug: String!, $locale: String!) {" +
                "  StorePageMapping {" +
                "    mapping(pageSlug: $pageSlug) {" +
                "      pageSlug" +
                "      pageType" +
                "      sandboxId" +
                "      productId" +
                "      createdDate" +
                "      updatedDate" +
                "      mappings {" +
                "        cmsSlug" +
                "        offerId" +
                "        offer(locale: $locale) {" +
                "          id" +
                "          namespace" +
                "          effectiveDate" +
                "        }" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        // Değişkenler
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageSlug", pageSlug);
        variables.put("locale", locale);

        // İstek gövdesini oluştur
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıkları oluştur
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteği gönder
            ResponseEntity<StorePageMappingResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    StorePageMappingResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Status code: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CatalogOfferResponse getCatalogOffer(String locale, String id, String namespace, String country) {
        String url = "https://store.epicgames.com/graphql";

        // GraphQL sorgusu
        String query = "query getCatalogOffer($locale: String!, $id: String!, $namespace: String!, $country: String!) {" +
                "  Catalog {" +
                "    catalogOffer(locale: $locale, id: $id, namespace: $namespace) {" +
                "      title" +
                "      id" +
                "      namespace" +
                "      developerDisplayName" +
                "      description" +
                "      effectiveDate" +
                "      viewableDate" +
                "      releaseDate" +
                "      allowPurchaseForPartialOwned" +
                "      offerType" +
                "      isCodeRedemptionOnly" +
                "      keyImages {" +
                "        type" +
                "        url" +
                "      }" +
                "      seller {" +
                "        id" +
                "        name" +
                "      }" +
                "      publisherDisplayName" +
                "      urlSlug" +
                "      tags {" +
                "        name" +
                "        groupName" +
                "      }" +
                "      price(country: $country) {" +
                "        totalPrice {" +
                "          originalPrice" +
                "          discountPrice" +
                "          currencyCode" +
                "          currencyInfo {" +
                "            decimals" +
                "          }" +
                "        }" +
                "      }" +
                "      categories {" +
                "        path" +
                "      }" +
                "      customAttributes {" +
                "        key" +
                "        value" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        // Değişkenleri oluştur
        Map<String, Object> variables = new HashMap<>();
        variables.put("locale", locale);
        variables.put("id", id);
        variables.put("namespace", namespace);
        variables.put("country", country);

        // İstek gövdesi
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıklar
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteğini gönder
            ResponseEntity<CatalogOfferResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    CatalogOfferResponse.class
            );

            // Başarılı durum kontrolü
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Durum kodu: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProductConfigurationResponse getProductConfiguration(String sandboxId) {
        String url = "https://store.epicgames.com/graphql";

        // GraphQL sorgusu
        String query = "query getStoreConfig($sandboxId: String!) {" +
                "  Product {" +
                "    sandbox(sandboxId: $sandboxId) {" +
                "      configuration {" +
                "        ... on StoreConfiguration {" +
                "          configs {" +
                "            developerDisplayName" +
                "            keyImages {" +
                "              type" +
                "              url" +
                "              alt" +
                "            }" +
                "            legalText" +
                "            pcReleaseDate" +
                "            productDisplayName" +
                "            privacyLink" +
                "            publisherDisplayName" +
                "            shortDescription" +
                "            socialLinks {" +
                "              platform" +
                "              url" +
                "            }" +
                "            supportedAudio" +
                "            supportedText" +
                "            tags {" +
                "              id" +
                "              name" +
                "              groupName" +
                "            }" +
                "            technicalRequirements {" +
                "              macos {" +
                "                minimum" +
                "                recommended" +
                "                title" +
                "              }" +
                "              windows {" +
                "                minimum" +
                "                recommended" +
                "                title" +
                "              }" +
                "            }" +
                "          }" +
                "        }" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        // Değişkenleri oluştur
        Map<String, Object> variables = new HashMap<>();
        variables.put("sandboxId", sandboxId);
        // İstek gövdesi
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıklar
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteğini gönder
            ResponseEntity<ProductConfigurationResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    ProductConfigurationResponse.class
            );

            // Başarılı durum kontrolü
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Durum kodu: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Description getProductDescription(String sandboxId) {
        String url = "https://store.epicgames.com/graphql";

        // GraphQL sorgusu
        String query = "query getStoreConfig($sandboxId: String!) {" +
                "  Product {" +
                "    sandbox(sandboxId: $sandboxId) {" +
                "      configuration {" +
                "        ... on HomeConfiguration {" +
                "          configs {" +
                "            longDescription" +
                "          }" +
                "        }" +
                "      }" +
                "    }" +
                "  }" +
                "}";

        // Değişkenleri oluştur
        Map<String, Object> variables = new HashMap<>();
        variables.put("sandboxId", sandboxId);
        // İstek gövdesi
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıklar
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteğini gönder
            ResponseEntity<Description> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Description.class
            );

            // Başarılı durum kontrolü
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Durum kodu: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProductResponse getProduct(String sandboxId) {
        String url = "https://egs-platform-service.store.epicgames.com/api/v1/egs/products/" + sandboxId + "?country=US&locale=en-US&store=EGS";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        HttpEntity<ProductResponse> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ProductResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    ProductResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Durum kodu: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ProductHomeConfig getProductHomeConfig(String sandboxId) {
        String url = "https://store.epicgames.com/graphql";

        String query = """
          query getProductHomeConfig($locale: String!, $sandboxId: String!) {
            Product {
              sandbox(sandboxId: $sandboxId) {
                configuration(locale: $locale) {
                  ... on HomeConfiguration {
                    configs {
                      keyImages {
                        ... on KeyImage {
                          type
                          url
                          alt
                        }
                        ... on Video {
                          type
                          url
                          alt
                        }
                      }
                      longDescription
                    }
                  }
                }
              }
            }
          }
          """;

        // Değişkenleri oluştur
        Map<String, Object> variables = new HashMap<>();
        variables.put("sandboxId", sandboxId);
        variables.put("locale", "en");
        // İstek gövdesi
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıklar
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteğini gönder
            ResponseEntity<ProductHomeConfig> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    ProductHomeConfig.class
            );

            // Başarılı durum kontrolü
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Durum kodu: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public VideoByIdResponse getVideoById(String videoId) {
        String url = "https://store.epicgames.com/graphql";

        String query = """
                query getVideoById($videoId: String!, $locale: String!) {
                  Video {
                    fetchVideoByLocale(videoId: $videoId, locale: $locale) {
                      recipe
                      mediaRefId
                    }
                  }
                }
                """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("videoId", videoId);
        variables.put("locale", "en");
        // İstek gövdesi
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıklar
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteğini gönder
            ResponseEntity<VideoByIdResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    VideoByIdResponse.class
            );

            // Başarılı durum kontrolü
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Durum kodu: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GetVideoResponse getVideo(String mediaRefId) {
        String url = "https://store.epicgames.com/graphql";

        String query = """
                query getVideo($mediaRefId: String!) {
                  Media {
                    getMediaRef(mediaRefId: $mediaRefId) {
                      accountId
                      outputs {
                        duration
                        url
                        width
                        height
                        key
                        contentType
                      }
                      namespace
                    }
                  }
                }
                """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("mediaRefId", mediaRefId);
        // İstek gövdesi
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        requestBody.put("variables", variables);

        // Başlıklar
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("User-Agent", "RestTemplateClient");

        // HttpEntity oluştur
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // GraphQL isteğini gönder
            ResponseEntity<GetVideoResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    GetVideoResponse.class
            );

            // Başarılı durum kontrolü
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                System.out.println("İstek başarısız oldu. Durum kodu: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
