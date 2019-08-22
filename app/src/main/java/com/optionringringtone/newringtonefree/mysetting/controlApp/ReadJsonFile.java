//package com.optionringringtone.newringtonefree.mysetting.controlApp;
//
//import android.os.Environment;
//import com.optionringringtone.newringtonefree.Untils.Configs;
//import com.optionringringtone.newringtonefree.object.detailcategory.Ringtone;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.io.File;
//import java.io.FileInputStream;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.nio.charset.Charset;
//import java.util.List;
//
//public class ReadJsonFile {
//    private Ringtone ringtone;
//    private List<Ringtone> ringtoneList;
//    public List<Ringtone> readFile(String name){
//        try {
//            JSONObject jsonObj = new JSONObject(loadFileJson(name));
//            JSONArray data  = jsonObj.getJSONArray("ringtones");
//            for (int i = 0; i < data.length(); i++) {
//                JSONObject c = data.getJSONObject(i);
//                String file_name = c.getString("file_name");
//
//                ringtone=new Ringtone();
//                ringtone.setFileName(file_name);
//
//                ringtoneList.add(ringtone);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ringtoneList;
//    }
//
//    private String loadFileJson(String name){
//        String jsonStr = null;
//        try {
//            File yourFile = new File(Environment.getExternalStorageDirectory(),
//                    Configs.PATH_STORAGE_CATEGORY+"/"+name+"/Most_Popular/ringtones.json");
//            FileInputStream stream = new FileInputStream(yourFile);
//            FileChannel fc = stream.getChannel();
//            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
//
//            jsonStr = Charset.defaultCharset().decode(bb).toString();
//            stream.close();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//        return jsonStr;
//    }
//}
