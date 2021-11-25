import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import com.json.FlattenJson;
import com.json.UnFlattenJson;
import com.json.model.Customer;
import com.json.utils.JsonUtils;

import java.util.Map;

public class Application {

    public static void main (String args[]) {
        // Flatten Json
        // 1. Read the json file as String
        // 2. Convert the JsonString into Model
        // 3. Convert the Model to JsonNode
        // 4. Invoke the FlattenJson.flattenJson(jsonNode)
        // It will flatten the json structure to flatten key values Map.
        String customerString = JsonUtils.readJsonFile("./customer.json");
        Customer customer = JsonUtils.stringToModel(customerString, Customer.class);
        JsonNode jsonNode = JsonUtils.stringToModel(customer.toString(), JsonNode.class);
        Map<String, ValueNode> flattenMap = FlattenJson.flattenJson(jsonNode);
        System.out.println("Flatten Map is ==> " + flattenMap.toString());


        Customer remakeCustomer = UnFlattenJson.unFlattenJson(flattenMap);
        System.out.println("Remake Customer Object ==> " + remakeCustomer.toString());
    }


}
