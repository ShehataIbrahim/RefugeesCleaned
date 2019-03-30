package com.refugees.portal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.refugees.portal.services.model.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsolidateService {
    @Value("${consolidate.host}")
    private String CONSOLIDATE_HOST;
    @Value("${consolidate.services.api}")
    private String CONSOLIDATE_API;
    @Value("${consolidate.services.key}")
    private String CONSOLIDATE_KEY;
    @Value("${consolidate.authKey}")
    private String CONSOLIDATE_AUTH_KEY;

    public String consoldiate(Patient patient,String method,String fromType,String toType) throws IOException {


        ConsolidateData data=new ConsolidateData();
        data.setBind_parameters(patient);
        data.setMethod(method);
        data.setResource_key("");
        ConsolidateRequest request=new ConsolidateRequest();

        List<ConsolidateData> consolidateDataList=new ArrayList<ConsolidateData>();
        consolidateDataList.add(data);
        request.setData(consolidateDataList);
        request.setFrom_type(fromType);
        request.setTo_type(toType);
        String responseText=processRequest(request,CONSOLIDATE_API);
        return responseText;
    }
    public String getResourceKey(String patientId) throws IOException
    {
        ResourceKeyRequest request=new ResourceKeyRequest(patientId);
        String responseText=processRequest(request,CONSOLIDATE_KEY);
        ResourceKeyResponse response=new Gson().fromJson(responseText,ResourceKeyResponse.class);
        return response.getData().get(0).getResource_key();
    }
    private String processRequest(BaseRequest request,String api) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String json=null;
        request.setAuth_key(CONSOLIDATE_AUTH_KEY);
        URL url = new URL(CONSOLIDATE_HOST+api);
        json = objectMapper.writeValueAsString(request);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_CREATED && responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + responseCode);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));
        String output;
        StringBuffer response=new StringBuffer();

        while ((output = br.readLine()) != null) {
            response.append(output);
        }
        return response.toString();

    }
}
