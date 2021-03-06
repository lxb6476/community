package com.lb.community.controller;

import com.lb.community.dto.AccessTokenDTO;
import com.lb.community.dto.GiteeTokenDTO;
import com.lb.community.dto.GiteeUser;
import com.lb.community.dto.GithubUser;
import com.lb.community.mapper.UserMapper;
import com.lb.community.model.User;
import com.lb.community.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;


    @GetMapping("callback")
    public String callback(@RequestParam(name = "code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        GiteeTokenDTO giteeTokenDTO = new GiteeTokenDTO();
        giteeTokenDTO.setGrant_type(grantType);
        giteeTokenDTO.setCode(code);
        giteeTokenDTO.setClient_id(clientId);
        giteeTokenDTO.setClient_secret(clientSecret);
        giteeTokenDTO.setRedirect_uri(redirectUrl);
//        System.out.println(code);

        String accessToken = giteeProvider.getAccessToken(giteeTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
//        System.out.println(user.getName());
//        System.out.println(user.getId());


        if (giteeUser != null) {

            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(giteeUser.getName());
            user.setAccountId(String.valueOf(giteeUser.getId()) );
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println(user);
            // ????????????cookie ??? session ???????????????????????????????????????redis??????
            response.addCookie(new Cookie("token",token));

            userMapper.insert(user);

            // ????????????  ???cookie???session
            request.getSession().setAttribute("user",giteeUser);
            // redirect:????????????????????????       ????????????????????????????????????????????????
            return "redirect:index";

        } else {
            // ???????????? ????????????
            return "redirect:index";
        }

    }
}
