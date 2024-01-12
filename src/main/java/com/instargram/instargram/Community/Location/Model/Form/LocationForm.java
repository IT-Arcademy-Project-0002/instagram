package com.instargram.instargram.Community.Location.Model.Form;

import com.instargram.instargram.Community.Location.Model.Entity.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationForm {
    private String ModifyLocationId;
    private String ModifyPlaceName;
    private String ModifyAddressName;
    private String ModifyRoadAddressName;
    private String Modify_x;
    private String Modify_y;

    public Location toLocation() {
        Location location = new Location();
        location.setLocationId(this.getModifyLocationId());
        location.setPlaceName(this.getModifyPlaceName());
        if (this.getModifyRoadAddressName() != null){
            location.setAddress(this.getModifyRoadAddressName());
        }else{
            location.setAddress(this.getModifyAddressName());
        }
        location.setX(this.getModify_x());
        location.setY(this.getModify_y());
        return location;
    }
}
