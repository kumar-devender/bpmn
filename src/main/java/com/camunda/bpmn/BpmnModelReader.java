package com.camunda.bpmn;

import org.camunda.bpm.engine.impl.util.json.JSONObject;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class BpmnModelReader {
    public static Optional<BpmnModelInstance> buildBpmnModelInstance() {
        try {
            HttpURLConnection con = getHttpURLConnection();
            BpmnModelInstance modelInstance = buildBpmnModelInstance(con.getInputStream());
            con.disconnect();
            return ofNullable(modelInstance);
        } catch (IOException e) {
            // No need to print anything here
        }
        return empty();
    }

    private static BpmnModelInstance buildBpmnModelInstance(InputStream inputStream) throws IOException {
        String content = getContent(inputStream);
        JSONObject obj = new JSONObject(content);
        InputStream targetStream = new ByteArrayInputStream(obj.getString("bpmn20Xml").getBytes());
        return Bpmn.readModelFromStream(targetStream);
    }

    private static String getContent(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    private static HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL("https://n35ro2ic4d.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        return con;
    }
}
