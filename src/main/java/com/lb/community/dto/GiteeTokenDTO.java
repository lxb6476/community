package com.lb.community.dto;

import lombok.Data;

@Data
public class GiteeTokenDTO {
    private String grant_type;
    private String code;
    private String client_id;
    private String redirect_uri;
    private String client_secret;
}
