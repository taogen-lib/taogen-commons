package com.taogen.commons.httpclient.springresttemplate;

import com.taogen.commons.httpclient.enums.HttpMethod;
import com.taogen.commons.httpclient.mockwebserver.MockWebServerTest;
import com.taogen.commons.httpclient.vo.*;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled
class SpringRestTemplateUtilTest extends BaseTest {

    @Autowired
    private SpringRestTemplateUtil springRestTemplateUtil;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void requestWithoutBody_get() throws InterruptedException {
        Function<String, HttpRequest> getHttpRequest = url -> {
            Map<String, List<Object>> queryParams = new HashMap<>();
            queryParams.put("id", Arrays.asList(1));
            queryParams.put("name", Arrays.asList("test"));
            queryParams.put("testChinese", Arrays.asList(CHINESE_TEST));
            HttpRequest httpRequest = HttpRequest.builder()
                    .url(url)
                    .method(HttpMethod.GET)
                    .headers(getBasicHeaders())
                    .queryParams(queryParams)
                    .build();
            return httpRequest;
        };
        Function<HttpRequest, HttpResponse> getResponse = httpRequest -> {
            HttpResponse httpResponse = springRestTemplateUtil.requestWithoutBody(httpRequest);
            System.out.println(httpResponse);
            return httpResponse;
        };
        MockWebServerTest.testRequestWithoutBody(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithJson() throws IOException, InterruptedException {
        Function<String, HttpRequestWithJson> getHttpRequest = url -> {
            String requestBody = "{\"id\": 1}";
            return HttpRequestWithJson.builder()
                    .url(url)
                    .method(HttpMethod.POST)
                    .headers(getBasicHeaders())
                    .json(requestBody)
                    .build();
        };

        Function<HttpRequestWithJson, HttpResponse> getResponse = httpRequest -> {
            try {
                return springRestTemplateUtil.requestWithJson(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithJson(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithFormUrlEncoded() throws InterruptedException, IOException {
        Function<String, HttpRequestWithForm> getHttpRequest = url -> {
            Map<String, List<Object>> formData = new HashMap<>();
            formData.put("id", Arrays.asList(1));
            formData.put("name", Arrays.asList("test", "test2"));
            return HttpRequestWithForm.builder()
                    .url(url)
                    .method(HttpMethod.POST)
                    .headers(getBasicHeaders())
                    .formData(formData)
                    .build();
        };

        Function<HttpRequestWithForm, HttpResponse> getResponse = httpRequest -> {
            try {
                return springRestTemplateUtil.requestWithFormUrlEncoded(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithUrlEncodedForm(mockWebServer, getHttpRequest, getResponse);
    }

    @Test
    void requestWithFormData() throws InterruptedException, IOException, URISyntaxException {
        Function<String, HttpRequestWithMultipart> getHttpRequest = url -> {
            Map<String, List<Object>> formData = new HashMap<>();
            formData.put("id", Arrays.asList(1));
            formData.put("name", Arrays.asList("test", "test2"));
            try {
                formData.put("file", Arrays.asList(
                        new FileSystemResource(ResourceUtils.getFile("classpath:test/test.jpg")),
                        new FileSystemResource(ResourceUtils.getFile("classpath:test/test.txt"))
                ));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            return HttpRequestWithMultipart.builder()
                    .url(url)
                    .method(HttpMethod.POST)
                    .headers(getBasicHeaders())
                    .formData(formData)
                    .build();
        };

        Function<HttpRequestWithMultipart, HttpResponse> getResponse = httpRequest -> {
            try {
                return springRestTemplateUtil.requestWithFormData(httpRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        MockWebServerTest.testPostWithMultipartForm(mockWebServer, getHttpRequest, getResponse);
    }


//    @Test
//    void getString() {
//        String url = new StringBuilder()
//                .append(userEndpointUrl)
//                .append("/")
//                .append(1)
//                .toString();
//        ResponseEntity<String> response = springRestTemplateUtil.request(
//                url, HttpMethod.GET, getBasicParam(), getBasicHeaders(), null, String.class);
//        System.out.println(response.toString());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        response.getHeaders();
//        Assumptions.assumingThat(response.getBody() != null, () -> {
//            assertEquals(String.class, response.getBody().getClass());
//        });
//    }
//
//    @Test
//    void getObject() {
//        String url = new StringBuilder()
//                .append(userEndpointUrl)
//                .append("/")
//                .append(1)
//                .toString();
//        ResponseEntity<User> response = springRestTemplateUtil.request(
//                url, HttpMethod.GET, getBasicParam(), getBasicHeaders(), null, User.class);
//        System.out.println(response.toString());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assumptions.assumingThat(response.getBody() != null, () -> {
//            assertEquals(User.class, response.getBody().getClass());
//        });
//    }
//
//    @Test
//    void getObjectList() {
//        String url = userEndpointUrl;
//        ResponseEntity<List<User>> response = springRestTemplateUtil.getObjectList(
//                url, getBasicParam(), getBasicHeaders(), null, new ParameterizedTypeReference<List<User>>() {
//                });
//        System.out.println(response.toString());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        Assumptions.assumingThat(response.getBody() != null && response.getBody().size() > 0, () -> {
//            assertEquals(User.class, response.getBody().get(0).getClass());
//        });
//    }
//
//    @Test
//    void postWithJson() throws JsonProcessingException {
//        String name = "test" + System.currentTimeMillis();
//        ResponseEntity<User> response = createUser(name);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        ResponseEntity<User> userResponse = getUserById(response.getBody().getId());
//        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
//        assertNotNull(userResponse.getBody());
//        assertEquals(name, userResponse.getBody().getName());
//    }
//
//    @Test
//    void putWithFormUrlEncoded() {
//        Long id = 1L;
//        User user = new User();
//        String newName = "test" + System.currentTimeMillis();
//        user.setId(id);
//        user.setName(newName);
//        ResponseEntity response = updateUserById(user);
//        System.out.println(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        ResponseEntity<User> userResponse = getUserById(id);
//        System.out.println(userResponse);
//        assertEquals(HttpStatus.OK, userResponse.getStatusCode());
//        assertNotNull(userResponse.getBody());
//        assertEquals(newName, userResponse.getBody().getName());
//    }
//
//    @Test
//    void deleteById() throws JsonProcessingException {
//        ResponseEntity<User> userResponse = createUser("test" + System.currentTimeMillis());
//        Long id = userResponse.getBody().getId();
//        assertNotNull(getUserById(id).getBody());
//        ResponseEntity response = springRestTemplateUtil.request(
//                userEndpointUrl + "/" + id, HttpMethod.DELETE, getBasicParam(), getBasicHeaders(), null, String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNull(getUserById(id).getBody());
//    }
//
//    @Test
//    void postWithFormData() throws FileNotFoundException {
//        MultiValueMap<String, String> headers = getBasicHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        File file = ResourceUtils.getFile("classpath:static/icon.jpg");
//        body.add("file", new FileSystemResource(file));
//        ResponseEntity<String> response = springRestTemplateUtil.request(
//                fileEndpointUrl + "/upload", HttpMethod.POST, getBasicParam(), headers, body, String.class);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        // check file is uploaded
//        MultiValueMap<String, String> param = getBasicParam();
//        param.add("fileUri", response.getBody());
//        ResponseEntity<Boolean> responseOfFileExist = springRestTemplateUtil.request(
//                fileEndpointUrl + "/doesFileExist", HttpMethod.GET, param, getBasicHeaders(), null, Boolean.class);
//        assertTrue(responseOfFileExist.getBody());
//        // delete file
//        ResponseEntity<String> responseOfDeleteFile = springRestTemplateUtil.request(fileEndpointUrl + "/deleteFile", HttpMethod.DELETE, param, getBasicHeaders(), null, String.class);
//        assertEquals(HttpStatus.OK, responseOfDeleteFile.getStatusCode());
//    }
//
//
//    private ResponseEntity<User> getUserById(Long id) {
//        ResponseEntity<User> userResponse = springRestTemplateUtil.request(
//                userEndpointUrl + "/" + id, HttpMethod.GET, getBasicParam(), getBasicHeaders(), null, User.class);
//        return userResponse;
//    }
//
//    private ResponseEntity<User> createUser(String name) throws JsonProcessingException {
//        String url = userEndpointUrl;
//        MultiValueMap headers = getBasicHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        ObjectMapper mapper = new ObjectMapper();
//        User user = new User();
//        user.setName(name);
//        String string = mapper.writeValueAsString(user);
//        ResponseEntity<User> response = springRestTemplateUtil.request(
//                url, HttpMethod.POST, getBasicParam(), headers, string, User.class);
//        return response;
//    }
//
//    private ResponseEntity updateUserById(User user) {
//        String url = userEndpointUrl + "/" + user.getId();
//        MultiValueMap headers = getBasicHeaders();
//        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
//        ResponseEntity response = springRestTemplateUtil.request(
//                url, HttpMethod.PUT, getBasicParam(), headers,
//                convertObjectToMultiValueMap(user), User.class);
//        return response;
//    }

}
