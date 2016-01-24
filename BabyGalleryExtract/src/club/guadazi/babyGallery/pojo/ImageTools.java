package club.guadazi.babyGallery.pojo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageTools {
    private static int width = 200;
    private static int height = 200;

    public static String getNicNameOfImageFile(String fileName) {
        int pointIndex = fileName.indexOf(".");
        String s1 = fileName.substring(0, pointIndex);
        String s2 = fileName.substring(pointIndex);

        return s1 + "_nic" + s2;
    }

    public static void zoomInImageFile(String srcFileName, String destFileName) {
        File srcFile = new File(srcFileName);
        File destFile = new File(destFileName);
        if (!srcFile.exists()) {
            return;
        }
        try {
            BufferedImage srcImage = null;
            if (srcFile.canRead()) {
                srcImage = ImageIO.read(srcFile);
            }

            int srcImageWidth = srcImage.getWidth();
            int srcImageHeight = srcImage.getHeight();
            double srcWidthTimes = (double) srcImageWidth / width;
            double srcHeightTimes = (double) srcImageHeight / height;
            double times = srcWidthTimes < srcHeightTimes ? srcHeightTimes : srcWidthTimes;
            BufferedImage newImage = new BufferedImage((int) (srcImageWidth / times), (int) (srcImageHeight / times), srcImage.getType());
            Graphics graphics = newImage.getGraphics();
            graphics.drawImage(srcImage, 0, 0, (int) (srcImageWidth / times), (int) (srcImageHeight / times), null);
            String imageFileType = "";
            int pointLastIndex = srcFileName.lastIndexOf(".");
            String perfix = srcFileName.substring(pointLastIndex + 1).toLowerCase();
            if (perfix.equals("jpeg") || perfix.equals("jpg")) {
                imageFileType = "JPEG";
            } else if (perfix.equals("png")) {
                imageFileType = "PNG";
            }
            ImageIO.write(newImage, imageFileType, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
