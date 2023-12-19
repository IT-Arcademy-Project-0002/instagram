package com.instargram.instargram.Member.Config.OAuth2.Model;

import java.util.HashMap;
import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    private String id;

    public KakaoUserInfo(Map<String, Object> attributes, String id) {
        this.attributes = attributes;
        this.id = id;
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getImage()
    {
        HashMap<String,Object> map = (HashMap<String, Object>) attributes.get("profile");
        return String.valueOf(map.get("thumbnail_image_url"));
    }
}
