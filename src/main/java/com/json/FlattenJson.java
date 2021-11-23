package com.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class FlattenJson {

    public static Map<String, ValueNode> flattenJson(JsonNode input) {
        Map<String, ValueNode> flattenMap = new LinkedHashMap<>();
        flattenJson(null, input, flattenMap);
        return flattenMap;
    }

    private static void flattenJson(String parent, JsonNode node, Map<String, ValueNode> map) {
        if (node instanceof ValueNode) {
            map.put(parent, (ValueNode) node);
        } else {
            if (node instanceof ArrayNode) {
                String prefix = (parent == null ? "" : parent + "[");
                ArrayNode arrayNode = (ArrayNode) node;
                for (int i = 0; i < arrayNode.size(); i++) {
                    flattenJson(prefix + i+ "]", arrayNode.get(i), map);
                }
            } else if (node instanceof ObjectNode) {
                String prefix = (parent == null ? "" : parent + ".");
                ObjectNode objectNode = (ObjectNode) node;
                for (Iterator<Map.Entry<String, JsonNode>> it = objectNode.fields(); it.hasNext(); ) {
                    Map.Entry<String, JsonNode> field = it.next();
                    flattenJson(prefix + field.getKey(), field.getValue(), map);
                }
            } else {
                throw new RuntimeException("unknown json node");
            }
        }
    }
}
