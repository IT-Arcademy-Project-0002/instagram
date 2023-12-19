package com.instargram.instargram.Member.Config.OAuth2.Model;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

    String getImage();
}

