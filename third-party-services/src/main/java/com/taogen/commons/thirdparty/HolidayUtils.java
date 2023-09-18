package com.taogen.commons.thirdparty;

import com.taogen.commons.httpclient.OkHttpUtil;
import com.taogen.commons.httpclient.enums.HttpMethod;
import com.taogen.commons.httpclient.vo.HttpRequest;
import com.taogen.commons.httpclient.vo.HttpResponse;
import com.taogen.commons.jsonparser.jackson.jackson.JacksonJsonUtil;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author taogen
 */
@Log4j2
public class HolidayUtils {
    public static final DateFormat DATE_FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

    public static String getHolidayOfYear(int year) throws IOException {
        String url = "http://timor.tech/api/holiday/year/" + year;
        HttpResponse httpResponse = OkHttpUtil.requestWithoutBody(HttpRequest.builder()
                .url(url)
                .method(HttpMethod.GET)
                .headers(getBasicHeaders())
                .build());
        return httpResponse.getBodyString(StandardCharsets.UTF_8);
    }

    public static boolean isHoliday(Date date) throws IOException {
        String url = "http://timor.tech/api/holiday/info/" + DATE_FORMAT_YYYY_MM_DD.format(date);
        log.debug("url: {}", url);
        HttpResponse httpResponse = OkHttpUtil.requestWithoutBody(HttpRequest.builder()
                .url(url)
                .method(HttpMethod.GET)
                .headers(getBasicHeaders())
                .build());
        String bodyString = httpResponse.getBodyString(StandardCharsets.UTF_8);
        log.debug("bodyString: {}", bodyString);
        return Optional.ofNullable(JacksonJsonUtil.jsonStrToJsonObject(bodyString))
                .map(jsonObject -> jsonObject.get("holiday"))
                .map(jsonObject -> jsonObject.get("holiday"))
                .map(jsonObject -> jsonObject.asBoolean())
                .orElse(false);
    }

    private static Map<String, List<Object>> getBasicHeaders() {
        Map<String, List<Object>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36"));
        return headers;
    }
}
