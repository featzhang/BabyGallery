package club.guadazi.babygallery.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.List;

import club.guadazi.babygallery.provider.remoteEntity.RemoteMessageEntity;

/**
 * 输入参数是 Object 数组，分别是请求地址、要上传的File 和messageData
 * 进度以 int
 * 返回值是 MessageData
 */
public abstract class AddNewMessageAsyncTask extends AsyncTask<Object, Integer, RemoteMessageEntity> {
    private final static String TAG = "AddNewMessageAsyncTask";

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private long totalSize;

    public AddNewMessageAsyncTask(Context context) {
        this.mContext = context;
    }


    @Override
    protected void onPreExecute() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("Uploading Picture...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected RemoteMessageEntity doInBackground(Object... params) {
        String url = (String) params[0];
        RemoteMessageEntity messageData = (RemoteMessageEntity) params[1];
        List<File> files = (List<File>) params[2];


        String serverResponse = null;

        HttpClient httpClient = new DefaultHttpClient();
        HttpContext httpContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(url);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        try {
            CustomMultipartEntity multipartContent = new CustomMultipartEntity(
                    new CustomMultipartEntity.ProgressListener() {
                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });
            if (files != null && files.size() > 0) {
                String fileNames = "";
                for (int i = 0; i < files.size(); i++) {
                    File file = files.get(i);
                    String name = file.getName();
                    fileNames += name;
                    FileBody fileBody = new FileBody(file, "image/" + name.substring(name.lastIndexOf(".") + 1, name.length()));
                    multipartContent.addPart("file" + (i + 1), fileBody);
                }
                multipartContent.addPart("fileNames", new StringBody(fileNames));
            }
            String messageDataString = gson.toJson(messageData);
            multipartContent.addPart("messageDataString", new StringBody(messageDataString));
            Log.i(TAG, "messageDataString " + messageDataString);

            totalSize = multipartContent.getContentLength();

            // Send it
            httpPost.setEntity(multipartContent);
            HttpResponse response = httpClient.execute(httpPost, httpContext);
            serverResponse = EntityUtils.toString(response.getEntity());
            Log.i(TAG, "response: " + serverResponse);
        } catch (Exception e) {
            e.printStackTrace();
            mProgressDialog.cancel();
            onFailed();
        }

//        return serverResponse;
        return TextUtils.isEmpty(serverResponse) ? null : gson.fromJson(serverResponse, RemoteMessageEntity.class);
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        mProgressDialog.setProgress((progress[0]));
    }

    @Override
    protected void onPostExecute(RemoteMessageEntity result) {
        System.out.println("result: " + result);
        mProgressDialog.dismiss();
        onFinish(result);
    }

    @Override
    protected void onCancelled() {
        System.out.println("cancle");
        onFailed();
    }

    public abstract void onFinish(RemoteMessageEntity result);

    public abstract void onFailed();
}
