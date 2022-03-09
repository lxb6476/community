package com.lb.community.dto;

import lombok.Data;

@Data
public class GiteeTokenResponse {
    private String access_token;
    private String token_type;
    private long expires_in;
    private String refresh_token;
    private String scope;
    private String create_at;

}
