package utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
public class HttpUtils {
    private static final MediaType JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    ;
    public static OkHttpClient mHttpClient = new OkHttpClient.Builder().
            connectTimeout(30, TimeUnit.SECONDS).
            readTimeout(30, TimeUnit.SECONDS).
            writeTimeout(30, TimeUnit.SECONDS).
            build();
    private static RequestBody formBody;

    //异步post请求
    public static void getResult(String url, String json, Callback callBack) {
        formBody = RequestBody.create(JSON, "reJson=" + json);
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        mHttpClient.newCall(request).enqueue(callBack);
    }

    //获取json key对应的value
    public static String getCode(String result, String key) {
        JsonParser jsonParser = new JsonParser();
        JsonElement el = jsonParser.parse(result);
        JsonObject jsonObj = null;
        if (el.isJsonObject()) {
            jsonObj = el.getAsJsonObject();
        }
        JsonElement code = jsonObj.get(key);
        if (code != null) {

            String codeString = code.getAsString();
            return codeString;
        } else {
            return null;
        }
    }

}
