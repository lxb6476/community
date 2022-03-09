package com.lb.community.provider;

import com.alibaba.fastjson.JSON;
import com.lb.community.dto.AccessTokenDTO;
import com.lb.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Component 把该类放入IOC容器中，使用该对象不需要自己创建，交给容器管理
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        final MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String json = JSON.toJSONString(accessTokenDTO);
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenstr = split[0];
            String token = tokenstr.split("=")[1];
            System.out.println(token);
            return token;
//            System.out.println(string);
//            return string;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {

        }
        return null;
    }

}
