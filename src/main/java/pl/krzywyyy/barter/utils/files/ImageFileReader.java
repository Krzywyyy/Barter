package pl.krzywyyy.barter.utils.files;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class ImageFileReader {
    public static String readAndEncode(String path) {
        try {
            byte[] imageByte = getByteArrayOfFile(path);
            return encodeByteArray(imageByte);
        } catch (IOException e) {
            return null;
        }
    }

    private static String encodeByteArray(byte[] imageByte) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(imageByte);
    }

    private static byte[] getByteArrayOfFile(String path) throws IOException {
        return FileUtils.readFileToByteArray(new File(path));
    }
}
