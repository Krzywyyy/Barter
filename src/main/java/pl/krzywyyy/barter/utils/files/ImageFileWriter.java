package pl.krzywyyy.barter.utils.files;

import pl.krzywyyy.barter.utils.properties.ProductImagesProperties;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public class ImageFileWriter {

    public static String decodeAndSave(String base64Url) {
        byte[] imageByte = getBytesFromBase64Url(base64Url);
        ByteArrayInputStream byteArrayInputStream = getByteArrayOfInputStream(imageByte);

        BufferedImage image;
        try {
            image = readImageAndCloseStream(byteArrayInputStream);

            return saveToFile(image);
        } catch (IOException e) {
            return null;
        }
    }

    private static byte[] getBytesFromBase64Url(String base64Url) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Url);
    }

    private static ByteArrayInputStream getByteArrayOfInputStream(byte[] imageByte) {
        return new ByteArrayInputStream(imageByte);
    }

    private static BufferedImage readImageAndCloseStream(ByteArrayInputStream byteArrayInputStream) throws IOException {
        BufferedImage image;
        image = ImageIO.read(byteArrayInputStream);
        byteArrayInputStream.close();
        return image;
    }

    private static String saveToFile(BufferedImage image) throws IOException {
        String fileName = createFileName();
        File file = new File(fileName);
        ImageIO.write(image, "png", file);
        return fileName;
    }

    private static String createFileName() {
        return ProductImagesProperties.FILE_DIRECTORY + UUID.randomUUID();
    }
}
