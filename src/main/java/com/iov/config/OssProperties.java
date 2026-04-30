package com.iov.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {
    private String region;
    private String bucket;
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
}
