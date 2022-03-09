package com.lb.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONToken;
import com.lb.community.dto.*;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GiteeProvider {
    public String getAccessToken(GiteeTokenDTO giteeTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        String json = JSON.toJSONString(giteeTokenDTO);
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url("https://gitee.com/oauth/token")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();

//            System.out.println(string);
//            GiteeTokenResponse giteeTokenResponse = JSON.parseObject(string, GiteeTokenResponse.class);
//            String accessToken = giteeTokenResponse.getAccess_token();
            // 不需要创建一个GiteeTokenResponse对象了
            String accessToken = (String) JSON.parseObject(string).get("access_token");
            return accessToken;


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GiteeUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();

//            System.out.println(string);
//            JSONObject object = JSON.parseObject(string);
//            System.out.println(object.get("name"));
            // 这个函数需要看一下 为什么json string中返回很多内容，最后只获取了一个用户类？
            // 明白了：JSON.parseObject()可以直接把string转为一个JSONObject类型的对象，该对象就是一个
            // map 可以根据key获取value值
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;

        } catch (IOException e) {

        }
        return null;
    }
}
