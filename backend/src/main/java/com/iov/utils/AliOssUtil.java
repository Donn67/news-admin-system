package com.iov.utils;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.OSSClientBuilder;
import com.aliyun.sdk.service.oss2.credentials.CredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.EnvironmentVariableCredentialsProvider;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectResult;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.iov.config.OssProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

@Component
public class AliOssUtil {
    @Autowired
    private OssProperties ossProperties;

    public String upload(String objectName, InputStream inputStream) {
        // 静态凭证提供者，从配置中读取
        CredentialsProvider provider = new StaticCredentialsProvider(
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret()
        );

        try (OSSClient client = OSSClient.newBuilder()
                .credentialsProvider(provider)
                .region(ossProperties.getRegion())
                .build()) {

            // 将输入流转换为 BinaryData
            byte[] bytes = inputStream.readAllBytes();
            BinaryData binaryData = BinaryData.fromBytes(bytes);

            PutObjectResult result = client.putObject(PutObjectRequest.newBuilder()
                    .bucket(ossProperties.getBucket())
                    .key(objectName)
                    .body(binaryData)
                    .build());

            // 生成访问 URL（根据 bucket 和 region 拼接）

            String url = String.format("https://%s.oss-%s.aliyuncs.com/%s",
                    ossProperties.getBucket(),
                    ossProperties.getRegion(),
                    objectName);

            System.out.printf("Upload success: %s, ETag: %s%n", url, result.eTag());
            return url;

        } catch (Exception e) {
            throw new RuntimeException("OSS 上传失败", e);
        }
    }
}
