package com.it.yanxuan.test;

import org.csource.fastdfs.*;


public class FastdfsTest {
    public static void main(String[] args) throws Exception {
        //加载全局配置文件
        ClientGlobal.init("E:\\idea-workspace\\dubbo\\yanxuan_parent\\yanxuan_seller_server\\src\\main\\resources\\fsatdfs\\fdfs_client.properties");

        //创建TrackerClient
        TrackerClient trackerClient = new TrackerClient();
        //连接TrackerServer
        TrackerServer trackerServer = trackerClient.getConnection();

        //获取StorageServer
        StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
        //创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);

        //上传文件
        /*
        参数一：本地文件的名称，上传文件的名称
        参数二：文件的扩展名
        参数三：描述信息
         */
        String[] resultList = storageClient.upload_file("E:/img/6.jpg", "jpg", null);
        for (String str : resultList) {
            System.out.println(str);
        }
    }
}

