package com.keviny.javacodtimer.controller;

import com.keviny.javacodtimer.service.timerService;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class mainController {

    private timerService timerService;

    @Autowired
    mainController(timerService timerService){
        this.timerService = timerService;
    }

    @PostMapping("/timer")
    ResponseEntity<String> time(@RequestBody String[] codes) throws JSONException, IOException {

        timerService.timerRequest("System.out.println(\"Hello World\");");

        String[] times = new String[codes.length];
        for(int i = 0; i < codes.length; i++){
            try {
                times[i] = timerService.timerRequest(codes[i]);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error: badRequest");
            }
        }

        JSONArray jsonArray = new JSONArray(times);

        return ResponseEntity.ok().body(jsonArray.toString());
    }

}
