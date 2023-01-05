package data.dataFromInnerResources;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonDictionaryWithAvailableIds {

    private static final String SRC_MAIN_RESOURCES_ENTITY_IDS_JSON = "src/main/resources/Entity_Ids.json";
    static Gson gson = new Gson();
    static Reader reader;

    static {

        try {
            reader = Files.newBufferedReader(Paths.get(SRC_MAIN_RESOURCES_ENTITY_IDS_JSON));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private JsonDictionaryWithAvailableIds() {
    }

    public static Map<String, AtomicInteger> getJsonObjectWithIdsAsMap() {

        Map<?, ?> map;
        Map<String, AtomicInteger> mapToReturn = new HashMap<>();

        try {

            map = gson.fromJson(reader, Map.class);

            for (Map.Entry<?, ?> entry : map.entrySet()) {
                mapToReturn.put(entry.getKey().toString(), new AtomicInteger(Integer.parseInt(entry.getValue().toString())));
            }

            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return mapToReturn;
    }

    public static void updateAll(Map<String, AtomicInteger> map) {

        try {
            gson.toJson(map, new FileWriter(String.valueOf(reader)));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
