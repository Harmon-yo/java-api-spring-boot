package school.sptech.harmonyospringapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

@RestController
public class S3Controller {
    @Autowired
    private AmazonS3 amazonS3Client;

    @PostMapping("/uploadRandomTxt")
    public String uploadRandomTxtToS3() {
        String bucketName = "bucket-teste-publico-d";
        String key = generateRandomFileName() + ".txt";
        String content = generateRandomTxtContent();

        byte[] contentBytes = content.getBytes();
        ByteArrayInputStream contentStream = new ByteArrayInputStream(contentBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentBytes.length);

        PutObjectRequest request = new PutObjectRequest(bucketName, key, contentStream, metadata);
        amazonS3Client.putObject(request);

        return "Arquivo TXT aleatório foi enviado com sucesso para o bucket público.";
    }

    private String generateRandomFileName() {
        // Gere um nome de arquivo aleatório, por exemplo, com base no timestamp.
        return "file" + System.currentTimeMillis();
    }

    private String generateRandomTxtContent() {
        // Gere conteúdo de arquivo TXT aleatório (exemplo).
        return "Conteúdo do arquivo TXT aleatório gerado pelo aplicativo Spring Boot.";
    }
}