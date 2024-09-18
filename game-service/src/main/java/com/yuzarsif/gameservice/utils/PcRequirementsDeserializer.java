package com.yuzarsif.gameservice.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzarsif.gameservice.client.steam.response.AppDetailsResponse;

import java.io.IOException;

public class PcRequirementsDeserializer extends JsonDeserializer<AppDetailsResponse.PcRequirements> {

    @Override
    public AppDetailsResponse.PcRequirements deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (node.isObject()) {
            return parsePcRequirements(node);
        } else {
            // If the node is not an object (e.g., an array), return null or throw an exception
            return null;
        }
    }

    private AppDetailsResponse.PcRequirements parsePcRequirements(JsonNode node) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(node, AppDetailsResponse.PcRequirements.class);
    }
}
