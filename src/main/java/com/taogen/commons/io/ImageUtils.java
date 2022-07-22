package com.taogen.commons.io;

import java.util.Base64;

/**
 * @author Taogen
 */
public class ImageUtils {
    /**
     * @param base64String for example, data:image/png;base64,iVBORw0KGgoAA...
     * @return
     */
    public static String getExtensionFromImageBase64(String base64String) {
        String[] strings = base64String.split(",");
        String extension;
        switch (strings[0]) {
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        return extension;
    }

    /**
     * @param base64String for example, data:image/png;base64,iVBORw0KGgoAA...
     * @return
     */
    public static byte[] getBytesFromImageBase64(String base64String) {
        String[] strings = base64String.split(",");
        byte[] bytes = Base64.getDecoder().decode(strings[1]);
        return bytes;
    }
}
