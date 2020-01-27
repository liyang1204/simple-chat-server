package com.smarttech.simplechatserver.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class RabbitMQServiceImpl implements RabbitMQService {

    private final String RABBIT_MQ_USERNAME = "guest";
    private final String RABBIT_MQ_PASSWORD = "guest";
    private final String RABBIT_MQ_URL = "http://localhost:15672/api/bindings";
    private final String RABBIT_MQ_PROPERTIES_KEY = "properties_key";
    private final String RABBIT_MQ_ROOM_NAME_PREFIX = "messages.";

    public List<String> getAllRoomNames() throws Exception {

        String response = getResponseFromUrl();
        List<String> bindings = getValuesForKey(response);
        List<String> roomNames = parseBindingsForRoomName(bindings);

        log.info("List of all active room names: {}", roomNames.toString());

        return roomNames;
    }

    private String getResponseFromUrl() throws Exception {
        URL url = new URL(RABBIT_MQ_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        String encoded = Base64.getEncoder().encodeToString((RABBIT_MQ_USERNAME + ":" + RABBIT_MQ_PASSWORD).getBytes(StandardCharsets.UTF_8));
        con.setRequestProperty("Authorization", "Basic " + encoded);

        InputStream inputStream = con.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        String currentLine;
        while ((currentLine = in.readLine()) != null) {
            response.append(currentLine);
        }
        in.close();

        return response.toString();
    }

    private List<String> getValuesForKey(String jsonStr) {

        JSONArray jsonArray = new JSONArray(jsonStr);

        List<String> values = IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString(RABBIT_MQ_PROPERTIES_KEY))
                .collect(Collectors.toList());

        return values;
    }

    private List<String> parseBindingsForRoomName(List<String> bindings) {

        Set<String> roomNames = new HashSet<>();
        bindings.forEach(binding -> {
            if (binding.contains(RABBIT_MQ_ROOM_NAME_PREFIX)) {
                roomNames.add(binding.replace(RABBIT_MQ_ROOM_NAME_PREFIX, ""));
            }
        });

        return new ArrayList<>(roomNames);
    }
}
