package com.it.yanxuan.seller.controller;

import com.it.yanxuan.common.utils.FastDFSClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传的处理器
 * @author aaaa
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {

    @PostMapping
    public ResponseEntity uploadFile(MultipartFile[] file) {
        Map<String, Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        System.out.println("图片个数为：=======" + file.length);
        //创建fastDFS的工具类
        FastDFSClient fastDFSClient = new FastDFSClient("classpath:/fsatdfs/fdfs_client.properties");
        //将文件上传到服务器
        for (MultipartFile multipartFile : file) {
            String originalFilename = multipartFile.getOriginalFilename();
            //获取文件的扩展名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            try {
                String path = fastDFSClient.uploadFile(multipartFile.getBytes(), extName);
                list.add(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //将全部文件路径放入map，并返回
        map.put("errno", 0);
        map.put("data", list);

        return new ResponseEntity(map, HttpStatus.OK);
    }
}
