package com.taogen.commons.network;

import com.taogen.commons.crypto.CryptoUtils;
import com.taogen.commons.datatypes.string.StringUtils;
import com.taogen.commons.network.vo.FormItem;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Taogen
 */
@Log4j2
public class HttpRequestUtil {

    public static final Pattern MULTIPART_SPLIT_PATTERN = Pattern.compile(
            "(--\\w+--)((?!\\1).)*", Pattern.DOTALL);

    public static final Pattern MULTIPART_FIELD_VALUE_PATTERN = Pattern.compile(
            "; name=\"(\\w+)\".*(\r\n\r\n|\r{2}|\n{2})(.*?)(\r\n|\n|\r)", Pattern.DOTALL);

    /**
     * field: group 1, filename: group 3, contentType: group 6, newline: group 8, content: group 9
     */
    public static final Pattern MULTIPART_HEX_FIELD_VALUE_PATTERN = Pattern.compile(
            "3b206e616d653d22(\\w+?)22(3b2066696c656e616d653d22(\\w+?)22(0d0a|0d|0a))*(436f6e74656e742d547970653a20(\\w+?)(0d0a|0d|0a))*.*(0d0a0d0a|0d0d|0a0a)(.*)", Pattern.DOTALL);

    public static final Pattern MULTIPART_BOUNDARY_PATTERN = Pattern.compile(
            "boundary=(.+)");

    public static final Pattern QUERY_STRING_FIELD_VALUE_PATTERN =
            Pattern.compile("([^?&].*?)=([^&]*)", Pattern.CASE_INSENSITIVE);

    /**
     * convert multiValueMap to queryString
     *
     * @param params
     * @return key1=value1&key2=value2&key3=value3
     */
    public static String multiValueMapToQueryString(Map<String, List<Object>> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (Map.Entry<String, List<Object>> entry : params.entrySet()) {
            String field = null;
            try {
                field = URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            List<Object> values = entry.getValue();
            if (values == null || values.isEmpty()) {
                sb.append(field).append("=");
            } else {
                for (int i = 0; i < values.size(); i++) {
                    String value = null;
                    try {
                        value = URLEncoder.encode(values.get(i).toString(), StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    sb.append(field)
                            .append("=")
                            .append(value);
                    if (i < values.size() - 1) {
                        sb.append("&");
                    }
                }
            }
            if (j < params.size() - 1) {
                sb.append("&");
            }
            j++;
        }
        return sb.toString();
    }

    /**
     * Convert queryString to multiValueMap
     * <p>
     * String.split() versus Pattern & Matcher
     * - In a small case such as this, it won't matter that much. However, if you have extremely large strings, it may be beneficial to use Pattern/Matcher directly.
     * - Most string functions that use regular expressions (such as matches(), split(), replaceAll(), etc.) makes use of Matcher/Pattern directly. Thus it will create a Matcher object every time, causing inefficiency when used in a large loop.
     * - Thus if you really want speed, you can use Matcher/Pattern directly and ideally only create a single Matcher object.
     * Reference https://stackoverflow.com/questions/24813430/java-parsing-strings-string-split-versus-pattern-matcher
     *
     * @param queryString key1=value1&key2=value2&key3=value3
     * @return
     */
    public static Map<String, List<Object>> queryStringToMultiValueMap(String queryString) {
        LinkedHashMap<String, List<Object>> multiValueMap = new LinkedHashMap<>();
        if (queryString == null || queryString.trim().isEmpty()) {
            return multiValueMap;
        }
        log.debug("queryString: {}", queryString);
        Matcher matcher = QUERY_STRING_FIELD_VALUE_PATTERN.matcher(queryString);
        while (matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2);
            if (multiValueMap.get(field) == null) {
                multiValueMap.put(field, new ArrayList<>());
            }
            if (value != null && !value.trim().isEmpty()) {
                multiValueMap.get(field).add(value);
            }
        }
        return multiValueMap;
    }

    /**
     * --boundary
     * {fieldname1}\r\n
     * \r\n
     * {fieldvalue1}\r\n
     * --boundary
     * {fieldname2}\r\n
     * \r\n
     * {fieldvalue2}\r\n
     * --boundary--
     *
     * @param data
     * @param boundary
     * @return
     * @throws IOException
     */
    public static byte[] multiValueMapToMultipartData(Map<String, List<Object>> data,
                                                      String boundary) throws IOException {
        if (data == null || data.isEmpty()) {
            return new byte[0];
        }
        // Result request body
        List<byte[]> byteArrays = new ArrayList<>();

        // Separator with boundary
        byte[] separator = (new StringBuilder()
                .append("--")
                .append(boundary)
                .append("\r\nContent-Disposition: form-data; name=")
                .toString()
                .getBytes(StandardCharsets.UTF_8));

        // Iterating over data parts
        for (Map.Entry<String, List<Object>> entry : data.entrySet()) {

            List<Object> values = entry.getValue();
            if (values != null && !values.isEmpty()) {
                for (Object value : values) {
                    // Opening boundary
                    byteArrays.add(separator);

                    // If value is type of Path (file) append content type with file name and file binaries, otherwise simply append key=value
                    if (value instanceof File) {
                        Path path = ((File) value).toPath();
                        String mimeType = Files.probeContentType(path);
                        byteArrays.add(new StringBuilder()
                                .append("\"")
                                .append(entry.getKey())
                                .append("\"; filename=\"")
                                .append(path.getFileName())
                                .append("\"\r\nContent-Type: ")
                                .append(mimeType)
                                .append("\r\n\r\n")
                                .toString()
                                .getBytes(StandardCharsets.UTF_8));
                        byteArrays.add(Files.readAllBytes(path));
                        byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
                    } else {
                        byteArrays.add(new StringBuilder()
                                .append("\"")
                                .append(entry.getKey())
                                .append("\"\r\n\r\n")
                                .append(value)
                                .append("\r\n")
                                .toString()
                                .getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
        }

        // Closing boundary
        byteArrays.add(new StringBuilder()
                .append("--")
                .append(boundary)
                .append("--")
                .toString()
                .getBytes(StandardCharsets.UTF_8));
        Integer byteLen = byteArrays.stream().map(item -> item.length).collect(Collectors.summingInt(Integer::intValue));
        byte[] result = new byte[byteLen];
        ByteBuffer buff = ByteBuffer.wrap(result);
        for (byte[] byteArray : byteArrays) {
            buff.put(byteArray);
        }
        return buff.array();
    }

    public static boolean formItemsEqual(List<FormItem> formItems1, List<FormItem> formItems2) {
        if (formItems1 == null && formItems2 == null) {
            return true;
        }
        if (formItems1 == null) {
            formItems1 = new ArrayList<>();
        }
        if (formItems2 == null) {
            formItems2 = new ArrayList<>();
        }
        if (formItems1.size() != formItems2.size()) {
            return false;
        }
        for (int i = 0; i < formItems1.size(); i++) {
            FormItem formItem1 = formItems1.get(i);
            FormItem formItem2 = formItems2.get(i);
            if (!formItem1.getIsFile().equals(formItem2.getIsFile())) {
                return false;
            }
            if (formItem1.getIsFile()) {
                if (!Arrays.equals(formItem1.getBytes(), formItem2.getBytes())) {
                    return false;
                }
            } else {
                if (!Objects.equals(formItem1.getValue(), formItem2.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<FormItem> convertBytesToFormItems(byte[] bytes, String boundary) {
        List<FormItem> formItems = new ArrayList<>();
        if (bytes == null || bytes.length == 0 || boundary == null || boundary.isEmpty()) {
            return formItems;
        }
        if (!boundary.startsWith("--")) {
            boundary = "--" + boundary;
        }
        String boundaryHex = CryptoUtils.bytesToHexString(boundary.getBytes(StandardCharsets.UTF_8));
        String bodyHex = CryptoUtils.bytesToHexString(bytes);
        String[] bodyHexParts = bodyHex.split(boundaryHex);
        int i = 0;
        for (String bodyHexPart : bodyHexParts) {
            Matcher matcher = MULTIPART_HEX_FIELD_VALUE_PATTERN.matcher(bodyHexPart);
            // field: group 1, filename: group 3, contentType: group 6, newline: group 8, content: group 9
            if (matcher.find()) {
                FormItem formItem = new FormItem();
                String nameHex = matcher.group(1);
                if (StringUtils.isEmpty(nameHex)) {
                    continue;
                }
                formItem.setName(new String(CryptoUtils.hexStringToBytes(nameHex), StandardCharsets.UTF_8));
                String newlineHex = matcher.group(8);
                String valueHex = matcher.group(9);
                valueHex = valueHex.substring(0, valueHex.length() - newlineHex.length() / 2);
                byte[] fieldValueBytes = CryptoUtils.hexStringToBytes(valueHex);
                formItem.setBytes(fieldValueBytes);
                if (StringUtils.isNotEmpty(matcher.group(6))) {
                    formItem.setContentType(new String(CryptoUtils.hexStringToBytes(matcher.group(6)), StandardCharsets.UTF_8));
                }
                String fileItemHex = matcher.group(3);
                if (StringUtils.isEmpty(fileItemHex)) {
                    formItem.setIsFile(false);
                    formItem.setValue(new String(formItem.getBytes(), StandardCharsets.UTF_8));
                } else {
                    String fileName = new String(CryptoUtils.hexStringToBytes(matcher.group(3)), StandardCharsets.UTF_8);
                    formItem.setIsFile(true);
                    formItem.setFilename(fileName);
                }
                formItems.add(formItem);
            }
        }
        return formItems;
    }

    public static Map<String, List<Object>> multipartDataToMultiValueMap(
            byte[] multipartData, String boundary) {
        if (multipartData == null || multipartData.length == 0) {
            return new LinkedHashMap<>();
        }
        if (!boundary.startsWith("--")) {
            boundary = "--" + boundary;
        }
        String multipartDataString = new String(multipartData, StandardCharsets.UTF_8);
        String[] split = multipartDataString.split(boundary);
        LinkedHashMap<String, List<Object>> multiValueMap = new LinkedHashMap<>();
        Arrays.stream(split).forEach(item -> {
            Matcher matcher = MULTIPART_FIELD_VALUE_PATTERN.matcher(item);
            int i = 0;
            while (matcher.find()) {
                log.debug("match item {} \n{}", ++i, matcher.group());
                String field = matcher.group(1);
                log.debug("field {}", field);
                String value = matcher.group(3);
                log.debug("value {}", value);
                if (multiValueMap.get(field) == null) {
                    multiValueMap.put(field, new ArrayList<>());
                }
                multiValueMap.get(field).add(value);
            }
        });
        return multiValueMap;
    }

    public static String getBoundaryByContentType(String contentType) {
        Matcher matcher = MULTIPART_BOUNDARY_PATTERN.matcher(contentType);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}