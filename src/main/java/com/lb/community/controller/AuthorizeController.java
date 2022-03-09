package com.lb.community.controller;

import com.lb.community.dto.AccessTokenDTO;
import com.lb.community.dto.GiteeTokenDTO;
import com.lb.community.dto.GiteeUser;
import com.lb.community.dto.GithubUser;
import com.lb.community.provider.GiteeProvider;
import com.lb.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

//    @Autowired
//    GithubProvider githubProvider;

    @Autowired
    GiteeProvider giteeProvider;

    @Value("${gitee.grant.type}")
    private String grantType;

    @Value("${gitee.client.id}")
    private String clientId;

    @Value("${gitee.client.secret}")
    private String clientSecret;

    @Value("${gitee.redirect.url}")
    private String redirectUrl;


    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code) {

        GiteeTokenDTO giteeTokenDTO = new GiteeTokenDTO();
        giteeTokenDTO.setGrant_type(grantType);
        giteeTokenDTO.setCode(code);
        giteeTokenDTO.setClient_id(clientId);
        giteeTokenDTO.setClient_secret(clientSecret);
        giteeTokenDTO.setRedirect_uri(redirectUrl);
//        System.out.println(code);

        String accessToken = giteeProvider.getAccessToken(giteeTokenDTO);
        GiteeUser user = giteeProvider.getUser(accessToken);
        System.out.println(user.getName());
        System.out.println(user.getId());
        return "index";
    }
}
