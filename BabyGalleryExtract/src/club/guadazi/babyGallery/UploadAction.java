package club.guadazi.babyGallery;

import club.guadazi.babyGalleryEJB.ifc.ImageData;
import club.guadazi.babyGalleryEJB.ifc.ImageIfc;
import club.guadazi.babyGalleryEJB.ifc.MessageData;
import club.guadazi.babyGalleryEJB.ifc.MessageIfc;
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
    private MessageIfc messageIfc = (MessageIfc) EjbUtils.getEjb(MessageIfc.class);
    private static final long serialVersionUID = 1L;
    //    private List<File> file;// 对应文件域的file，封装文件内容
//    private List<String> fileContentType;// 封装文件类型
//    private List<String> fileFileName;// 封装文件名
    private File file1;
    private File file2;
    private File file3;
    private File file4;
    private File file5;
    private File file6;
    private String fileNames;
    private String fileTypes;
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
        ArrayList<File> file = new ArrayList<>();

        if (file1 != null) {
            file.add(file1);
        }
        if (file2 != null) {
            file.add(file2);
        }
        if (file3 != null) {
            file.add(file3);
        }
        if (file4 != null) {
            file.add(file4);
        }
        if (file5 != null) {
            file.add(file5);
        }
        if (file6 != null) {
            file.add(file6);
        }

        String[] fileNameList = fileNames.split(",");
        if (file != null && file.size() > 0) {
            List<String> fileIds = new ArrayList<>();
            for (int i = 0; i < file.size(); i++) {
                long currentTimeMillis = System.currentTimeMillis();
                String tempFileName = currentTimeMillis + "_" + userId;
                String fileName = fileNameList[i];
                String fileNamePrefix = fileName.substring(fileName.lastIndexOf("."));
                tempFileName += fileNamePrefix;
                ImageData imageData = new ImageData();
                imageData.setImageName(tempFileName);
                int imageId = imageIfc.add(imageData);
                log.info("file name: " + fileName + " id: " + imageId);

                String imageSavePath = "/Users/Mariaaron/temp";
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
        int id = messageIfc.addNewMessage(messageData);
        messageData.setId(id);

        return SUCCESS;
    }

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public File getFile2() {
        return file2;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }

    public File getFile3() {
        return file3;
    }

    public void setFile3(File file3) {
        this.file3 = file3;
    }

    public File getFile4() {
        return file4;
    }

    public void setFile4(File file4) {
        this.file4 = file4;
    }

    public File getFile5() {
        return file5;
    }

    public void setFile5(File file5) {
        this.file5 = file5;
    }

    public File getFile6() {
        return file6;
    }

    public void setFile6(File file6) {
        this.file6 = file6;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    public String getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(String fileTypes) {
        this.fileTypes = fileTypes;
    }

    public String getMessageDataString() {
        return messageDataString;
    }

    public void setMessageDataString(String messageDataString) {
        this.messageDataString = messageDataString;
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }
}