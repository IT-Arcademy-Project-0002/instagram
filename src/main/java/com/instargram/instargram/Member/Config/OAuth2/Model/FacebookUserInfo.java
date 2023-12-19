package com.instargram.instargram.Member.Config.OAuth2.Model;

import java.util.Map;

public class FacebookUserInfo implements OAuth2UserInfo{
    private Map<String, Object> attributes;

    public FacebookUserInfo(Map<String, Object> attributes) {

        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProvider() {
        return "FACEBOOK";
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
        return String.valueOf(attributes.get("picture"));
    }
}
