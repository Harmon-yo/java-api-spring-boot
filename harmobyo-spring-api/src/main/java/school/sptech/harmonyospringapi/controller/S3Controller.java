package school.sptech.harmonyospringapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@RestController
public class S3Controller {
    @Value("${aws.accessKey}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    private final AmazonS3 amazonS3Client;

    public S3Controller(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @PostMapping("/upload-log/{email}/{metodo}/{timestamp}")
    public String uploadRandomTxtToS3(@PathVariable String email, @PathVariable String metodo, @PathVariable String timestamp) {
        String bucketName = "bucket-harmonyo-publico";
        String key = generateRandomFileName() + ".txt";
        String content = email + " acessou o método " + metodo + " as " + timestamp;

        byte[] contentBytes = content.getBytes();
        ByteArrayInputStream contentStream = new ByteArrayInputStream(contentBytes);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(contentBytes.length);

        PutObjectRequest request = new PutObjectRequest(bucketName, key, contentStream, metadata);
        amazonS3Client.putObject(request);

        return "Arquivo TXT aleatório foi enviado com sucesso para o bucket público.";
    }

    // Implemente ou substitua os métodos geradores de nome de arquivo e conteúdo aleatório aqui.

    private String generateRandomFileName() {
        return UUID.randomUUID().toString();
    }

    private String generateRandomTxtContent() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        return "Conteúdo aleatório: " + randomNumber;
    }

}