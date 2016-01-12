package club.guadazi.babyGallery;

import club.guadazi.babyGalleryEJB.ifc.ImageData;
import club.guadazi.babyGalleryEJB.ifc.ImageIfc;
import club.guadazi.babyGalleryEJB.ifc.MessageData;
import club.guadazi.common.EjbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
// Dragostea din tel

public class UploadAction extends ActionSupport {
    private Log log = LogFactory.getLog(UploadAction.class);
    private ImageIfc imageIfc = (ImageIfc) EjbUtils.getEjb(ImageIfc.class);
    private static final long serialVersionUID = 1L;
    private List<File> file;// 对应文件域的file，封装文件内容
    private List<String> fileContentType;// 封装文件类型
    private List<String> fileFileName;// 封装文件名
    private String messageDataString;
    private MessageData messageData;

    /**
     * 接收 messageData和图片文件
     *
     * @return 图片文件对象
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        if (StringUtils.isEmpty(messageDataString)) {
            return ERROR;
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        messageData = gson.fromJson(messageDataString, MessageData.class);
        Integer userId = messageData.getUserId();
        if (file != null && file.size() > 0) {
            List<String> fileIds = new ArrayList<>();
            for (int i = 0; i < file.size(); i++) {
                long currentTimeMillis = System.currentTimeMillis();
                String tempFileName = currentTimeMillis + "_" + userId;
                String fileName = fileFileName.get(i);
                String fileNamePrefix = fileName.substring(fileFileName.lastIndexOf("."));
                tempFileName += fileNamePrefix;
                ImageData imageData = new ImageData();
                imageData.setImageName(tempFileName);
                int imageId = imageIfc.add(imageData);

                String imageSavePath = "";
                InputStream is = new FileInputStream(file.get(i));
                // 创建输出流，生成新文件
                OutputStream os = new FileOutputStream(new File(imageSavePath + File.separator + tempFileName));
                // 将InputStream里的byte拷贝到OutputStream
                IOUtils.copy(is, os);
                os.flush();
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);

                fileIds.add("" + imageId);
            }
            if (fileIds.size() > 0) {
                String fileIdString = "";
                for (int i = 0; i < fileIds.size(); i++) {
                    final String fileId = fileIds.get(i);
                    if (i > 0) {
                        fileIdString += ":::";
                    }
                    fileIdString += fileId;
                }
                messageData.setImageIds(fileIdString);
            }
        }

        return SUCCESS;
    }

    public void setFile(List<File> file) {
        this.file = file;
    }

    public void setFileContentType(List<String> fileContentType) {
        this.fileContentType = fileContentType;
    }

    public void setFileFileName(List<String> fileFileName) {
        this.fileFileName = fileFileName;
    }

    public void setMessageDataString(String messageDataString) {
        this.messageDataString = messageDataString;
    }

    public MessageData getMessageData() {
        return messageData;
    }
}