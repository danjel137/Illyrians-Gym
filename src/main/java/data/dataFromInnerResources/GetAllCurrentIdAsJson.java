package data.dataFromInnerResources;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GetAllCurrentIdAsJson {
    private GetAllCurrentIdAsJson() {
    }

    public static JsonNode get() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Properties properties = new Properties();
        JsonNode idsInJson = null;

        FileReader fileReader = null;
        try {
            fileReader = new FileReader("src/main/resources/config.properties");
            properties.load(fileReader);
            idsInJson = objectMapper.readTree(properties.getProperty("entityIdsFilePath"));
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {
            assert fileReader != null;
            fileReader.close();
        }
        return idsInJson;
    }
}
