package data.dataFromInnerResources;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonDictionaryWithAvailableIds {

    String entityIdsFilePath = "src/main/resources/Entity_Ids.json";

    private JsonDictionaryWithAvailableIds() {
    }

    public static Map getJsonObjectWithIdsAsMap() {
        Map map = null;

        try {

            Gson gson = new Gson();

            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/Entity_Ids.json"));

            map = gson.fromJson(reader, Map.class);

            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return map;
    }

    public static void updateAll(Map<String, Integer> map) {

    }

}
