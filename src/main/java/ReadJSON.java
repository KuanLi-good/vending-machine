import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadJSON {
    private JSONArray json;
    private HashMap<String, String> accounts = new HashMap<>();


    public ReadJSON(String jsonPath) {
        try {
            json = (JSONArray) new JSONParser().parse(new FileReader(jsonPath));
            this.readCreditCards();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void readCreditCards() {

        Iterator<JSONObject> iterator = json.iterator();
        while(iterator.hasNext()) {
            JSONObject jObject = iterator.next();
            accounts.put(jObject.get("name").toString(), jObject.get("number").toString());
        }

//        for (Map.Entry<String, String> entry : accounts.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

    }

    public HashMap<String, String> getAccounts() {
        return accounts;
    }

}
