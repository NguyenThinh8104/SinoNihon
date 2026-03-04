package utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AIService {
    private static final String API_KEY = "AIzaSyAO8rqc1ArR82FZ7ymUdj9AVxhR3S1bW3Y";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public static String getAIResponse (String systemPrompt, String userMessage){
        try {
            String combinedPrompt = "System Instruction: " + systemPrompt + "\n\nUserMessage: " + userMessage;
            JsonObject textPart = new JsonObject();
            textPart.addProperty("text", combinedPrompt);
            JsonArray partsArray = new JsonArray();
            partsArray.add(textPart);
            JsonObject contentObj = new JsonObject();
            contentObj.add("parts", partsArray);
            JsonArray contentArray = new JsonArray();
            contentArray.add(contentObj);
            JsonObject responseObj = new JsonObject();
            responseObj.add("contents", contentArray);

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(15)).build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(responseObj.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonResponse = new JsonParser().parseString(response.body()).getAsJsonObject();
                String aiText = jsonResponse.getAsJsonArray("candidates")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("content")
                        .getAsJsonArray("parts")
                        .get(0).getAsJsonObject()
                        .get("text").getAsString();

                aiText = aiText.replace("**", "");
                aiText = aiText.replace("*", "-");
                return aiText;
            } else {
                return "Lỗi từ máy chủ AI (Code " + response.statusCode() + ")";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Mất kết nối tới dịch vụ AI. Hãy kiểm tra lại internet hoặc API Key";
        }
    }
}
