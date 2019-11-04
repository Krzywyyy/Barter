package pl.krzywyyy.barter.utils.files;

import org.apache.commons.io.FileUtils;
import pl.krzywyyy.barter.utils.properties.ProductImagesProperties;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class ImageFileWriter {

    public static String decodeAndSave(String base64Url) {
        byte[] imageByte = Base64.getDecoder().decode(base64Url);

        String fileName = createFileName();
        try {
            FileUtils.writeByteArrayToFile(new File(fileName), imageByte);
            return fileName;
        } catch (IOException e) {
            return ProductImagesProperties.NO_IMAGE;
        }
    }

    private static String createFileName() {
        return ProductImagesProperties.FILE_DIRECTORY + UUID.randomUUID();
    }
}
