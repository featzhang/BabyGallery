package club.guadazi.babygallery.net;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.InputStream;

import club.guadazi.babygallery.provider.dao.ImageDao;
import club.guadazi.babygallery.provider.entity.ImageEntity;
import club.guadazi.babygallery.provider.sync.ImageManager;
import club.guadazi.babygallery.util.ConstantValues;
import club.guadazi.babygallery.util.FileUtils;

public abstract class DownloadImageAsyncTask extends AsyncTask<String, Integer, ImageEntity> {

    private Context mContext;

    public DownloadImageAsyncTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected ImageEntity doInBackground(String... params) {
        String url = params[0];
        int imageId = Integer.parseInt(params[1]);
        int userId = Integer.parseInt(params[2]);

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url + "?imageId=" + imageId + "&userId=" + userId);
        ImageEntity imageEntity = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                Header[] fileNames = httpResponse.getHeaders("FileName");
                String fileName = fileNames[0].getValue();
                InputStream inputStream = httpEntity.getContent();

                imageEntity = new ImageEntity();
                String localImageFilePath = url.equals(ConstantValues.REQUEST_IMAGE_NIC) ? ImageManager.getThumbnailPathByImageName(mContext, fileName) : ImageManager.getImagePathByImageName(mContext, fileName);
                imageEntity.setImageLocalName(fileName);
                imageEntity.setRemoteImageId(imageId);
                long id = new ImageDao(mContext).addIfNotExist(imageEntity);
                imageEntity.setId((int) id);
                File file = new File(localImageFilePath);
                FileUtils.saveToFile(inputStream, file);
                onFinish(imageEntity);
            }
        } catch (Exception e) {
        }
        return imageEntity;
    }

    public abstract void onFinish(ImageEntity imageEntity);

}
