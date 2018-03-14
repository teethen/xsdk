package com.teethen.sdk.xupdater;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author xingq on 2018/2/17.
 */
public class AppInfoUtil {

    public AppInfo requestAppInfo(String url) {
        AppInfo info = null;

        try {
            //UpdaterUtil.logger(url);
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    result.append(line);
                }

                UpdaterUtil.closeQuietly(br);

                info = parseResult(result.toString());
            }
        } catch (Exception e) {
            UpdaterUtil.loggerError(e);
        }

        return info;
    }

    private AppInfo parseResult(String result) {
        if (TextUtils.isEmpty(result)) {
            return null;
        }

        AppInfo appInfo = null;

        try {
            JSONObject object = new JSONObject(result);
            if (object != null) {
                appInfo = new AppInfo();
                appInfo.appName = object.optString("name");
                appInfo.appVersionName = object.optString("version");
                appInfo.appChangeLog = object.optString("log");
                appInfo.appDownloadUrl = object.optString("url");
                if (object.has("binary")) {
                    JSONObject binary = object.optJSONObject("binary");
                    if (binary != null) {
                        appInfo.appSize = binary.optLong("size");
                    }
                } else {
                    appInfo.appSize = object.optLong("size");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return appInfo;
    }

}
