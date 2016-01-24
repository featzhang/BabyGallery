package club.guadazi.babyGallery;

import club.guadazi.babyGallery.pojo.ImageTools;
import club.guadazi.babyGalleryEJB.ifc.ImageData;
import club.guadazi.babyGalleryEJB.ifc.ImageIfc;
import club.guadazi.common.EjbUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadImageAction extends ActionSupport {
    private Log log = LogFactory.getLog(DownloadImageAction.class);
    private static final int BUFFER_128K = 128;
    private int imageId;
    private int userId;
    private ImageIfc imageIfc = (ImageIfc) EjbUtils.getEjb(ImageIfc.class);
    private String imageType;

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void doDownload() {
        log.info("doDownload: request download image id=" + imageId);
        String path = "/Users/Mariaaron/temp";
        ImageData imageData = imageIfc.findById(imageId);
        if (imageData == null) {
            log.info("can not find image with id is " + imageId);
            return;
        }
        String imageName = imageData.getImageName();
        String fileName = path + File.separator + imageName;
        String originFileName = imageName;
        if (imageType != null && imageType.equals("nic")) {
            fileName = ImageTools.getNicNameOfImageFile(fileName);
        }
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }
        log.info("image file name is " + fileName);
        HttpServletResponse resp = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String range = request.getHeader("Range");
        int status = HttpServletResponse.SC_OK;
        if (range == null) {
            range = "byte=0-";
        } else {
            //通过指定下载区域下载，使用206表示支持断点下载
            status = HttpServletResponse.SC_PARTIAL_CONTENT;
        }
        String startPos = null;
        if (null != range && range.startsWith("byte=")) {
            String[] values = range.split("=")[1].split("-");
            startPos = values[0];
        }
        resp.setStatus(status);
        BufferedInputStream in = null;
        OutputStream output = null;
        try {
            output = new BufferedOutputStream(resp.getOutputStream());
            int bufferSize = BUFFER_128K;
            long fileSize = file.length();
            long needReadLen = fileSize;
            if (status == HttpServletResponse.SC_PARTIAL_CONTENT) {
                // 设置断点续传的Content-Range传输字节和总字节
                resp.setHeader("Content-Range", "bytes " + startPos + "-"
                        + (fileSize - 1) + "/" + fileSize);
                needReadLen = fileSize - Long.parseLong(startPos) + 1;
            }
            resp.setHeader("FileName", originFileName);
            in = new BufferedInputStream(new FileInputStream(file));
            in.skip(Long.parseLong(startPos));
            bufferSize = (needReadLen > (long) bufferSize) ? bufferSize
                    : (int) needReadLen;
            // log.info("needReadLen : " + needReadLen + "     startPos : "
            // + startPos + "    bufferSize : " + bufferSize);
            byte[] b = new byte[bufferSize];
            int i = 0;
            while ((i = in.read(b, 0, bufferSize)) != -1) {
                output.write(b, 0, i);
                output.flush();
                needReadLen -= i;
                if (needReadLen <= 0) {
                    break;
                }
                bufferSize = (needReadLen > (long) bufferSize) ? bufferSize
                        : (int) needReadLen;
            }
            resp.flushBuffer();
            log.info("download complete");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
