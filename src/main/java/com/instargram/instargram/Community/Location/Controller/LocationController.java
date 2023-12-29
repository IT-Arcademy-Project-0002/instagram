package com.instargram.instargram.Community.Location.Controller;

import com.instargram.instargram.Community.Location.Model.DTO.LocationDTO;
import com.instargram.instargram.Community.Location.Service.LocationService;
import lombok.Builder;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Builder
@RequestMapping("/location")
public class LocationController {

    private LocationService locationService;

    @GetMapping("/search")
    public String init() {
       return "Location/location";
    }

    @GetMapping(value = "/keyword", produces = "application/json")
    @ResponseBody
    public List<LocationDTO> keywordSearchJson(Model model, @RequestParam("keyword") String keyword) throws JSONException {

        List<LocationDTO> locationResult = this.locationService.getCoordinateByKeyword(keyword);
        model.addAttribute("locationResult", locationResult);

        return locationResult;
    }
}
