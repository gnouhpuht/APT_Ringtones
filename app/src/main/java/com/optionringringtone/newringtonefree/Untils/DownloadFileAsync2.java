package com.optionringringtone.newringtonefree.Untils;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadFileAsync2 extends AsyncTask<String, String, String> {
    private downloadInterface mDownloadInterface;
    private String name;
    private String nameFolder;
    private boolean faild = false;

    public DownloadFileAsync2(String name, String nameFolder, downloadInterface mDownloadInterface) {
        this.name = name;
        this.nameFolder = nameFolder;
        this.mDownloadInterface = mDownloadInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mDownloadInterface != null) mDownloadInterface.onStart();
    }

    @Override
    protected String doInBackground(String... aurl) {

        int count;
        try {
            URL url = new URL(aurl[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            // this will be useful so that you can show a tipical 0-100%
            // progress bar
            int lenghtOfFile = conection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            String path = Environment.getExternalStorageDirectory().toString() + Configs.PATH_STORAGE_CATEGORY;
            File mFile = new File(path);
            mFile.mkdirs();
            // Output stream
            OutputStream output = new FileOutputStream(path + name);

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

            // TODO giai nen
            // Tạo thư mục Output nếu nó không tồn tại.
            File folder = new File(path + nameFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            // Tạo một buffer (Bộ đệm).
            byte[] buffer = new byte[1024];

            ZipInputStream zipIs = null;
            try {
                // Tạo đối tượng ZipInputStream để đọc file từ 1 đường dẫn (path).
                zipIs = new ZipInputStream(new FileInputStream(path + name));

                ZipEntry entry = null;
                // Duyệt từng Entry (Từ trên xuống dưới cho tới hết)
                while ((entry = zipIs.getNextEntry()) != null) {
                    String entryName = entry.getName();
                    String outFileName = path + nameFolder + File.separator + entryName;
                    System.out.println("Unzip: " + outFileName);

                    if (entry.isDirectory()) {
                        // Tạo các thư mục.
                        new File(outFileName).mkdirs();
                    } else {
                        // Tạo một Stream để ghi dữ liệu vào file.
                        FileOutputStream fos = new FileOutputStream(outFileName);

                        int len;
                        // Đọc dữ liệu trên Entry hiện tại.
                        while ((len = zipIs.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }

                        fos.close();
                    }
                }

                try {
                    File mFile2 = new File(path + name);
                    mFile2.delete();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                faild = true;
                e.printStackTrace();
                try {
                    File mFile2 = new File(path + name);
                    mFile2.delete();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    zipIs.close();
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {
            faild = true;
            Log.e("Error: ", e.getMessage());
            try {
                String path = Environment.getExternalStorageDirectory().toString() + Configs.PATH_STORAGE_CATEGORY;
                File mFile = new File(path + name);
                mFile.delete();

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
//        Log.d(ConstantsApp.TAG, "onProgressUpdate: " + progress[0]);
        if (mDownloadInterface != null)
            mDownloadInterface.onProgressUpdate(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String unused) {
        if (faild) {
            mDownloadInterface.onFaildDownload();
            return;
        }
        if (mDownloadInterface != null)
            mDownloadInterface.onComplete(Environment.getExternalStorageDirectory().toString()
                    + Configs.PATH_STORAGE_CATEGORY + name);
    }

    public interface downloadInterface {
        void onProgressUpdate(int progress);

        void onFaildDownload();

        void onComplete(String path);

        void onStart();
    }
}
