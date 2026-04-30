package com.iov.controller;

import com.iov.pojo.Result;
import com.iov.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String uniqueName= "image/"+UUID.randomUUID() + "_" + originalFilename;
        String url = aliOssUtil.upload(uniqueName, file.getInputStream());
        return Result.success(url);
    }
}
