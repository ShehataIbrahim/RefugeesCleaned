package com.refugees.portal.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.refugees.portal.db.health.model.InterviewDisplay;
import com.refugees.portal.services.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class ConsolidateService {
    @Value("${consolidate.host}")
    private String CONSOLIDATE_HOST;
    @Value("${consolidate.services.api}")
    private String CONSOLIDATE_API;
    @Value("${consolidate.services.resourcekey}")
    private String CONSOLIDATE_Resource_KEY;
    @Value("${consolidate.services.interview}")
    private String INTERVIEW_ANSWERS_API;
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
        String responseText=processRequest(request, CONSOLIDATE_Resource_KEY);
        ResourceKeyResponse response=new Gson().fromJson(responseText,ResourceKeyResponse.class);
        return response.getData().get(0).getResource_key();
    }
    public void consolidateInterviewAnswer(String patientId, InterviewDisplay question,String answerString) throws IOException
    {
        ResourceKeyRequest request=new ResourceKeyRequest(patientId);
        String responseText=processRequest(request, CONSOLIDATE_Resource_KEY);
        ResourceKeyResponse response=new Gson().fromJson(responseText,ResourceKeyResponse.class);
        String resourceKey=response.getData().get(0).getResource_key();
        if(resourceKey==null)
            throw new IOException("Invalid Patient resource key");
        ConsolidateInterviewRequest consolidateInterviewRequest=new ConsolidateInterviewRequest();
        consolidateInterviewRequest.setFrom_type("3001#0000#0");
        consolidateInterviewRequest.setTo_type("0200#0100#1");
        InterviewConsolidationData item = new InterviewConsolidationData(resourceKey);
        item.setMethod("insert");
        InterviewAnswerData answer = new InterviewAnswerData();
        answer.setIfaDate(new Date());
        answer.setInterview_answer(answerString);
        answer.setInterview_answer_type(question.getAnswerType());
        answer.setInterview_category_id(String.valueOf(question.getCategoryId()));
        answer.setInterview_id(String.valueOf(question.getInterviewId()));
        answer.setInterview_version(String.valueOf(question.getCategoryVersion()));
        item.setBind_parameters(answer);
        consolidateInterviewRequest.getData().add(item);
        processRequest(consolidateInterviewRequest,CONSOLIDATE_API);

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
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            StringBuffer response=new StringBuffer();

            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            throw new RuntimeException("Failed : HTTP error code : "
                    + responseCode+" due to "+response);
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

    public Map<String,InterviewAnswerBaseData> getPatientAnswers(String patientId) throws IOException{
        final Map<String,InterviewAnswerBaseData> data=new HashMap<>();
        String resource_key=getResourceKey(patientId);
        PatientAnswersRequest resourceKeyRequest=new PatientAnswersRequest(resource_key);
        String responseText= processRequest(resourceKeyRequest, INTERVIEW_ANSWERS_API);
        PatientAnswersResponse response=new Gson().fromJson(responseText, PatientAnswersResponse.class);
        if(response.getData()!=null && !response.getData().isEmpty())
        {
            response.getData().stream().forEach(answer->{
                data.put(answer.getInterview_category_id()+"_"+answer.getInterview_version()+"_"+answer.getInterview_id(),answer);
            });
        }
        return data;
    }
}
