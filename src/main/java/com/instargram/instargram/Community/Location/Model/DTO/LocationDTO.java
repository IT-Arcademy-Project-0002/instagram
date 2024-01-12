package com.instargram.instargram.Community.Location.Model.DTO;

import com.instargram.instargram.Community.Location.Model.Entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO {

    private String locationId;
    private String placeName;
    private String addressName;
    private String roadAddressName;
    private String x;
    private String y;

    public LocationDTO(Location location){
        locationId = location.getLocationId();
        placeName = location.getPlaceName();
        addressName = location.getAddress();
        roadAddressName = location.getAddress();
        x = location.getX();
        y = location.getY();
    }
}
