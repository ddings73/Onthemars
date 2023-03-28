package onthemars.back.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.tika.Tika;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {
    private static final Tika tika = new Tika();

    private static final List<String> validImgType = Arrays.asList("image/jpeg", "image/pjpeg", "image/png", "image/gif", "image/bmp",
        "image/x-windows-bmp", "image/svg+xml");

    public static boolean validImgFile(InputStream inputStream){
        try {
            String mimeType = tika.detect(inputStream);

            log.info("mimeType => {}", mimeType);

            return validImgType.stream().anyMatch(type->type.equalsIgnoreCase(mimeType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
