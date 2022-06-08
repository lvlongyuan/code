package com.yuangege.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yuangege.springboot.utils.baiduaip.Plant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 元哥哥
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/discern")
public class DiscernController {

    @Value("${files.upload.cache}")
    private String fileUploadPath;

    /**
     *文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUUID);

        if(!uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().mkdirs();
        }
        file.transferTo(uploadFile);
        String imageUrl = fileUploadPath + fileUUID;
        String result = Plant.plant(imageUrl);
        uploadFile.delete();
        if (uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().delete();
        }
        return result;
    }
}