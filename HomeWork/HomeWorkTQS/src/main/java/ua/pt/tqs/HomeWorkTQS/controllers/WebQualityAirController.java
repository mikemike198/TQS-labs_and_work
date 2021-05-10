package ua.pt.tqs.HomeWorkTQS.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.pt.tqs.HomeWorkTQS.services.WebQualityService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class WebQualityAirController {

    private WebQualityService service = new WebQualityService();

    @GetMapping("/home")
    public String showHome(Model model) {
        //JSONObject forecasts = this.service.getDataFromNearestCity();
        JSONParser parser = new JSONParser();
        JSONObject current = null;
        JSONObject forecasts = null;
        JSONObject pollution = null;
        JSONObject data = (JSONObject) service.getDataFromNearestCity();
        current = (JSONObject) data.get("current");
        forecasts = (JSONObject) current.get("weather");
        pollution = (JSONObject) current.get("pollution");

        model.addAttribute("city", data.get("city"));
        model.addAttribute("country", data.get("country"));
        model.addAttribute("pressure", forecasts.get("pr"));
        model.addAttribute("temperature", forecasts.get("tp"));
        model.addAttribute("wind_speed", forecasts.get("ws"));
        model.addAttribute("humidity", forecasts.get("hu"));
        model.addAttribute("icon", forecasts.get("ic"));
        model.addAttribute("aqiUS", pollution.get("aqius"));
        model.addAttribute("aqiCN", pollution.get("aqicn"));
        model.addAttribute("mainUS", pollution.get("mainus"));
        model.addAttribute("mainCN", pollution.get("maincn"));
        model.addAttribute("search", new Search());

        return "home";
    }

    @PostMapping("/home/search")
    public String showCity(@ModelAttribute Search search, Model model) {
        JSONParser parser = new JSONParser();
        JSONObject data = null;
        JSONObject current = null;
        JSONObject forecasts = null;
        JSONObject pollution = null;
        data = (JSONObject) service.getDataFromSpecificCity(search.getCity(), search.getState(), search.getCountry());

        if (data == null) {
            return "errorPage";
        }

        current = (JSONObject) data.get("current");
        forecasts = (JSONObject) current.get("weather");
        pollution = (JSONObject) current.get("pollution");

        model.addAttribute("city", data.get("city"));
        model.addAttribute("country", data.get("country"));
        model.addAttribute("pressure", forecasts.get("pr"));
        model.addAttribute("temperature", forecasts.get("tp"));
        model.addAttribute("wind_speed", forecasts.get("ws"));
        model.addAttribute("humidity", forecasts.get("hu"));
        model.addAttribute("icon", forecasts.get("ic"));
        model.addAttribute("aqiUS", pollution.get("aqius"));
        model.addAttribute("aqiCN", pollution.get("aqicn"));
        model.addAttribute("mainUS", pollution.get("mainus"));
        model.addAttribute("mainCN", pollution.get("maincn"));
        model.addAttribute("search", new Search());

        return "home";
    }

}
