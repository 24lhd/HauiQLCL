package duong.update.code;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by D on 12/19/2016.
 */

public class UpdateApp {
    /**
     * Phương thức tải file .apk từ 1 url về máy và cài đặt khi đã tải xong
     * @param activity đối tượng gọi cần 1 Activity
     * @param appName Tên file .apk được tải về
     * @param urlApp link url chứa file apk
     * @param content nội dung của trạng thái update
     * @param title       title cua thong báo
     */
    public void getAndInstallAppLication(final Activity activity, String appName, String urlApp, String content,String title) {
        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        final String fileName = appName;
        destination += fileName;
        final Uri uri = Uri.parse("file://" + destination);
        //Delete update file if exists
        File file = new File(destination);
        if (file.exists())
            //file.delete() - test this, I think sometimes it doesnt work
            file.delete();
        //get url of app on server
        String url = "https://firebasestorage.googleapis.com/v0/b/haitet2017-80e44.appspot.com/o/1.apk?alt=media&token=970670a2-cd22-48bb-b1e6-69fc30b85492";
        //set downloadmanager
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(content);
        request.setTitle(title);
        //set destination
        request.setDestinationUri(uri);
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = manager.enqueue(request);

        //set BroadcastReceiver to install app when .apk is downloaded
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + fileName)), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(install);
                activity.unregisterReceiver(this);
                activity.finish();
            }
        };
        //register receiver for when .apk download is compete
        activity.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
}
