package com.jiangzhiyan.vhr.utils;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传工具类
 */
public class FastDFSUtil {

    private static StorageClient1 client1;

    static {
        try {
            ClientGlobal.initByProperties("fastdfs-client.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            client1 = new StorageClient1(trackerServer, null);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
    }

    public static String upload(MultipartFile file){
        String oldFileName = file.getOriginalFilename();
        try {
            return client1.upload_file1(file.getBytes(), oldFileName.substring(oldFileName.lastIndexOf(".")+1),null);
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
