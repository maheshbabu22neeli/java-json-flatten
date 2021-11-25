package com.json;

import com.fasterxml.jackson.databind.node.ValueNode;
import com.json.model.Customer;
import com.json.utils.JsonUtils;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.util.ObjectUtils;

import java.util.Map;

public class UnFlattenJson {

    private static final String DOT = ".";
    private static final String OPEN_BRACES = "[";
    private static final String CLOSING_BRACES = "]";

    public static Customer unFlattenJson(Map<String, ValueNode> flattenMap) {

        JSONObject unFlattenJsonObject = new JSONObject();

        for (Map.Entry<String, ValueNode> entry : flattenMap.entrySet()) {
            Object nodeValue = entry.getValue();
            createObjectNode(entry.getKey(), nodeValue, unFlattenJsonObject);
        }

        System.out.println("unFlattenJsonObject ==> " + unFlattenJsonObject);

        return JsonUtils.stringToModel(unFlattenJsonObject.toString().replace("\\\"", ""), Customer.class);
    }

    private static JSONObject createObjectNode(String nodeKey, Object nodeValue, Object objectNode) {

        JSONObject unFlattenJson;
        int dotIndex = nodeKey.indexOf(DOT);
        if (dotIndex == -1) {
            unFlattenJson = (JSONObject) objectNode;
            unFlattenJson.put(nodeKey, nodeValue.toString());

        } else {
            String parent = nodeKey.substring(0, dotIndex);
            String child = nodeKey.substring(dotIndex + 1);

            if (parent.contains(OPEN_BRACES) && parent.contains(CLOSING_BRACES)) {
                unFlattenJson = (JSONObject) objectNode;

                String actualListName = parent.substring(0, parent.indexOf(OPEN_BRACES));
                int arrayIndex = Integer.parseInt(parent.substring(parent.indexOf(OPEN_BRACES) + 1, parent.indexOf(CLOSING_BRACES)));

                if (!ObjectUtils.isEmpty(unFlattenJson.get(actualListName))) {
                    JSONArray listObject = (JSONArray) unFlattenJson.get(actualListName);
                    if (listObject.size() < arrayIndex + 1) {
                        for (int i = listObject.size(); i < arrayIndex + 1; i++) {
                            listObject.add(i, new JSONObject());
                        }
                        createObjectNode(child, nodeValue, listObject.get(arrayIndex));
                    }

                    if (listObject.get(arrayIndex) != null) {
                        createObjectNode(child, nodeValue, listObject.get(arrayIndex));
                    } else {
                        listObject.add(arrayIndex, createObjectNode(child, nodeValue, new JSONObject()));
                    }
                } else {
                    unFlattenJson = (JSONObject) objectNode;
                    unFlattenJson.put(actualListName, new JSONArray());
                    JSONArray listObject = (JSONArray) unFlattenJson.get(actualListName);
                    if (listObject.size() < arrayIndex + 1) {
                        for (int i = listObject.size(); i < arrayIndex + 1; i++) {
                            listObject.add(i, new JSONObject());
                        }
                        createObjectNode(child, nodeValue, listObject.get(arrayIndex));
                    } else {
                        listObject.add(arrayIndex, createObjectNode(child, nodeValue, new JSONObject()));
                    }
                }
            } else {
                unFlattenJson = (JSONObject) objectNode;
                if (!ObjectUtils.isEmpty(unFlattenJson.get(parent))) {
                    unFlattenJson.put(parent, createObjectNode(child, nodeValue, unFlattenJson.get(parent)));
                } else {
                    unFlattenJson.put(parent, createObjectNode(child, nodeValue, new JSONObject()));
                }
            }
        }

        return unFlattenJson;
    }

}
