package com.imadelfetouh.adminservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.imadelfetouh.moderatorservice.dal.configuration.Executer;
import com.imadelfetouh.moderatorservice.dal.configuration.SessionType;
import com.imadelfetouh.moderatorservice.dal.db.RoleDalDB;
import com.imadelfetouh.moderatorservice.dal.db.UserDalDB;
import com.imadelfetouh.moderatorservice.dal.queryexecuter.SetupDatabase;
import com.imadelfetouh.moderatorservice.model.dto.UserDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import com.imadelfetouh.moderatorservice.rabbit.RabbitConfiguration;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModeratorUserTests {

    @BeforeAll
    static void setupDatabase() {
        Executer<Void> executer = new Executer<>(SessionType.WRITE);
        executer.execute(new SetupDatabase());

        Channel channel = RabbitConfiguration.getInstance().getChannel();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().correlationId("testcorr").build();

        try {
            String user1 = new JSONObject()
                    .put("userId", "u123").put("username", "imad").put("password", "imad").put("role", "USER").put("photo", "imad.jpg")
                    .put("profile", new JSONObject().put("profileId", "p123").put("bio", "Hello").put("location", "Helmond").put("website", "imad.nl")).toString();

            String user2 = new JSONObject()
                    .put("userId", "u1234").put("username", "peter").put("password", "peter").put("role", "USER").put("photo", "peter.jpg")
                    .put("profile", new JSONObject().put("profileId", "p1234").put("bio", "Hello").put("location", "Helmond").put("website", "peter.nl")).toString();

            channel.basicPublish("adduserexchange", "", properties, user1.getBytes());
            channel.basicPublish("adduserexchange", "", properties, user2.getBytes());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void getUsersCorrect() {
        UserDalDB userDalDB = new UserDalDB();

        ResponseModel<List<UserDTO>> responseModel = userDalDB.getUsers();

        Assertions.assertEquals(2, responseModel.getData().size());
        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());
        Assertions.assertEquals("u123", responseModel.getData().get(0).getUserId());
        Assertions.assertEquals("u1234", responseModel.getData().get(1).getUserId());
    }

    @Test
    @Order(2)
    void changeRoleCorrect() throws InterruptedException {
        RoleDalDB roleDalDB = new RoleDalDB();

        ResponseModel<Void> responseModel = roleDalDB.changeRole("u123");

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());

        Thread.sleep(2000);

        String url = "http://localhost:8081/auth/signin";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("username", "imad");
        map.add("password", "imad");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseEntity.getBody(), JsonObject.class);

        Assertions.assertEquals("u123", jsonObject.get("userId").getAsString());
        Assertions.assertEquals("imad", jsonObject.get("username").getAsString());
        Assertions.assertEquals("MODERATOR", jsonObject.get("role").getAsString());

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());

    }

    @Test
    @Order(4)
    void deleteUserCorrect() throws InterruptedException {
        UserDalDB userDalDB = new UserDalDB();
        ResponseModel<Void> responseModel = userDalDB.deleteUser("u123");

        Assertions.assertEquals(ResponseType.CORRECT, responseModel.getResponseType());

        Thread.sleep(2000);

        String url = "http://localhost:8081/auth/signin";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("username", "imad");
        map.add("password", "imad");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        try{
            restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }
        catch (HttpClientErrorException e) {
            Assertions.assertEquals(400, e.getStatusCode().value());
        }
    }
}
