package ua.pt.tqs.HomeWorkTQS.controllers;

import org.dom4j.rule.Mode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.pt.tqs.HomeWorkTQS.entities.Cache;
import ua.pt.tqs.HomeWorkTQS.services.Statistics;
import ua.pt.tqs.HomeWorkTQS.services.WebQualityService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
public class WebQualityAirController {

    private WebQualityService service;

    public WebQualityAirController(WebQualityService service) {
        this.service = service;
    }

    //Front End

    @GetMapping("/")
    public ModelAndView show() {
        return new ModelAndView("index");
    }

    @GetMapping("/home")
    public ModelAndView showHome(Model model) {
        //JSONObject forecasts = this.service.getDataFromNearestCity();
        JSONParser parser = new JSONParser();
        JSONObject current = null;
        JSONObject forecasts = null;
        JSONObject pollution = null;
        JSONObject data = (JSONObject) service.getDataFromNearestCity();

        if (data == null) {
            return new ModelAndView("errorPage");
        }

        if(data.get("current") == null) {
            return new ModelAndView("errorPage");
        }
        current = (JSONObject) data.get("current");
        if(current.get("weather") == null) {
            return new ModelAndView("errorPage");
        }
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

        return new ModelAndView("home");
    }

    @PostMapping("/home/search")
    public ModelAndView showCity(@ModelAttribute Search search, Model model) {
        JSONParser parser = new JSONParser();
        JSONObject data = null;
        JSONObject current = null;
        JSONObject forecasts = null;
        JSONObject pollution = null;
        data = (JSONObject) service.getDataFromSpecificCity(search.getCity(), search.getState(), search.getCountry());

        if (data == null) {
            return new ModelAndView("errorPage");
        }

        if(data.get("current") == null) {
            return new ModelAndView("errorPage");
        }
        current = (JSONObject) data.get("current");
        if(current.get("weather") == null) {
            return new ModelAndView("errorPage");
        }
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

        return new ModelAndView("home");
    }


    //REST API

    @RequestMapping(value = "/api/search" , method = RequestMethod.GET)
    public ResponseEntity<List<Cache>> getAllCache(@RequestParam(value = "city", required = false) Optional<String> city, @RequestParam(value = "country", required = false) Optional<String> country) {
        List<Cache> result = service.getCache(city,country);
        if (result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }

    @RequestMapping(value = "/api/cache/stats" , method = RequestMethod.GET)
    public ResponseEntity<List<Statistics>> getCityCountryCache(@RequestParam(value = "city", required = false) Optional<String> city, @RequestParam(value = "country", required = false) Optional<String> country) {

        List<Statistics> result = service.getStatistics(city,country);

        if (result != null) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

    }




}

