package org.example.configuration;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfiguration {

    @Value("${minio.access.key}")
    private String accessKey;

    @Value("${minio.access.secret}")
    private String secretKey;

    @Value("${minio.url}")
    private String minioUrl;

    @Bean
    @Primary
    public MinioClient minioClient() throws NoSuchAlgorithmException, KeyManagementException {
        MinioClient client = new MinioClient.Builder()
                .credentials(accessKey, secretKey)
                .endpoint(minioUrl)
                .build();

        client.ignoreCertCheck();
        return client;
    }
}
