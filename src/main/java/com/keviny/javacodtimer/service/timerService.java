package com.keviny.javacodtimer.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class timerService {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    public String timerRequest(String exec) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObj = new JSONObject();
        JSONObject jsonObj2 = new JSONObject();
        JSONObject filesJson = new JSONObject();
        JSONObject filesnewFileOptionJson1 = new JSONObject();
        JSONObject filesnewFileOptionJson2 = new JSONObject();

        JSONArray files = new JSONArray();
        JSONArray newFileOptionArray = new JSONArray();

        String code = "import java.util.*;\n\npublic class Main {\n    public static void main(String[] args) {\n      "
                + exec +
                "\n  }\n}";

        jsonObj.put("name", "Java");
        jsonObj.put("title", "Java Hello World");
        jsonObj.put("version", "11");
        jsonObj.put("mode", "java");
        jsonObj.put("description", "null");
        jsonObj.put("extension", "java");
        jsonObj.put("concurrentJobs", 2);
        jsonObj.put("languageType", "programming");
        jsonObj.put("active", true);

        jsonObj2.put("language", "java");
        jsonObj2.put("docs", true);

        jsonObj2.put("tutorials", false);
        jsonObj2.put("cheatsheets", true);
        jsonObj2.put("filesEditable", true);
        jsonObj2.put("filesDeletable", true);

        filesJson.put("name", "Main.java");
        filesJson.put("content", code);
        files.put(filesJson);

        filesnewFileOptionJson1.put("helpText", "New Java file");
        filesnewFileOptionJson1.put("name", "NewClass${i}.java");
        filesnewFileOptionJson1.put("content", "public class NewClass${i} {\n\n  public String sayHelloFromNewClass(){\n    return \"Hello from New Class\";\n  }\n\n}");
        newFileOptionArray.put(filesnewFileOptionJson1);

        filesnewFileOptionJson2.put("helpText", "Add Dependencies");
        filesnewFileOptionJson2.put("name", "build.gradle");
        filesnewFileOptionJson2.put("content", "apply plugin:'application'\nmainClassName = 'HelloWorld'\n\nrun { standardInput = System.in }\nsourceSets { main { java { srcDir './' } } }\n\nrepositories {\n    jcenter()\n}\n\ndependencies {\n    // add dependencies here like following\n    // implementation group: 'org.apache.commons', name: 'commons-lang3', version: '3.9'\n}");
        newFileOptionArray.put(filesnewFileOptionJson2);

        jsonObj2.put("files", files);
        jsonObj2.put("newFileOptions", newFileOptionArray);
        jsonObj.put("properties", jsonObj2);

        jsonObj.put("visibility", "public");

        RequestBody body = RequestBody.create(String.valueOf(jsonObj), JSON);
        Request request = new Request.Builder()
                .url("https://onecompiler.com/api/code/exec")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        JSONObject responseJson = new JSONObject(response.body().string());


        String error = responseJson.getString("stderr");

            if (!error.equals("null")) {
                error = error.substring(0, error.indexOf("\n"));
                return error;
            }

            String executionTime = responseJson.getString("executionTime");

            return executionTime;
        }

}

