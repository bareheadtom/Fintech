package com.faas.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileResource {

    private Logger LOGGER = LoggerFactory.getLogger(FileResource.class);

    private URL url;

    public FileResource(String path) {
        url = this.getClass().getResource(path);
    }

    public String read() {
        try {
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String data = null;
            StringBuilder sb = new StringBuilder();
            while((data = br.readLine()) != null) {
                sb.append(data);
            }
            br.close();
            isr.close();
            is.close();
            return sb.toString();
        } catch (IOException e) {
            LOGGER.error("文件读取失败：{}", e);
        }
        return null;
    }

}
