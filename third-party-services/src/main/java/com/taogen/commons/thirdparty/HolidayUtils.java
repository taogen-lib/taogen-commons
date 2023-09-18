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

    /**
     * Get holiday of year
     *
     * @param year
     * @return E.g. {"code":0,"holiday":{"01-01":{"holiday":true,"name":"元旦","wage":3,"date":"2023-01-01","rest":1},"01-02":{"holiday":true,"name":"元旦","wage":2,"date":"2023-01-02","rest":1},"01-21":{"holiday":true,"name":"除夕","wage":3,"date":"2023-01-21","rest":2},"01-22":{"holiday":true,"name":"初一","wage":3,"date":"2023-01-22","rest":1},"01-23":{"holiday":true,"name":"初二","wage":3,"date":"2023-01-23","rest":1},"01-24":{"holiday":true,"name":"初三","wage":3,"date":"2023-01-24","rest":1},"01-25":{"holiday":true,"name":"初四","wage":2,"date":"2023-01-25","rest":1},"01-26":{"holiday":true,"name":"初五","wage":2,"date":"2023-01-26","rest":1},"01-27":{"holiday":true,"name":"初六","wage":2,"date":"2023-01-27","rest":1},"01-28":{"holiday":false,"name":"春节后补班","wage":1,"after":true,"target":"春节","date":"2023-01-28","rest":1},"01-29":{"holiday":false,"name":"春节后补班","wage":1,"after":true,"target":"春节","date":"2023-01-29","rest":1},"04-05":{"holiday":true,"name":"清明节","wage":3,"date":"2023-04-05","rest":67},"04-23":{"holiday":false,"name":"劳动节前补班","wage":1,"target":"劳动节","after":false,"date":"2023-04-23","rest":1},"04-29":{"holiday":true,"name":"劳动节","wage":2,"date":"2023-04-29","rest":7},"04-30":{"holiday":true,"name":"劳动节","wage":2,"date":"2023-04-30","rest":1},"05-01":{"holiday":true,"name":"劳动节","wage":3,"date":"2023-05-01","rest":1},"05-02":{"holiday":true,"name":"劳动节","wage":3,"date":"2023-05-02","rest":1},"05-03":{"holiday":true,"name":"劳动节","wage":3,"date":"2023-05-03","rest":1},"05-06":{"holiday":false,"name":"劳动节后补班","after":true,"wage":1,"target":"劳动节","date":"2023-05-06","rest":2},"06-22":{"holiday":true,"name":"端午节","wage":3,"date":"2023-06-22","rest":2},"06-23":{"holiday":true,"name":"端午节","wage":3,"date":"2023-06-23","rest":1},"06-24":{"holiday":true,"name":"端午节","wage":2,"date":"2023-06-24","rest":1},"06-25":{"holiday":false,"name":"端午节后补班","wage":1,"target":"端午节","after":true,"date":"2023-06-25","rest":1},"09-29":{"holiday":true,"name":"中秋节","wage":3,"date":"2023-09-29","rest":11},"09-30":{"holiday":true,"name":"中秋节","wage":3,"date":"2023-09-30","rest":1},"10-01":{"holiday":true,"name":"国庆节","wage":3,"date":"2023-10-01","rest":1},"10-02":{"holiday":true,"name":"国庆节","wage":3,"date":"2023-10-02","rest":1},"10-03":{"holiday":true,"name":"国庆节","wage":2,"date":"2023-10-03","rest":1},"10-04":{"holiday":true,"name":"国庆节","wage":2,"date":"2023-10-04","rest":1},"10-05":{"holiday":true,"name":"国庆节","wage":2,"date":"2023-10-05","rest":1},"10-06":{"holiday":true,"name":"国庆节","wage":2,"date":"2023-10-06","rest":1},"10-07":{"holiday":false,"after":true,"wage":1,"name":"国庆节后补班","target":"国庆节","date":"2023-10-07","rest":1},"10-08":{"holiday":false,"after":true,"wage":1,"name":"国庆节后补班","target":"国庆节","date":"2023-10-08","rest":1},"12-31":{"holiday":true,"name":"元旦","wage":2,"date":"2023-12-31","rest":60}}}
     * @throws IOException
     */
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
