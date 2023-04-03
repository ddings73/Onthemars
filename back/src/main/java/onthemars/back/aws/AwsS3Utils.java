package onthemars.back.aws;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import onthemars.back.common.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component @Slf4j
@RequiredArgsConstructor
public class AwsS3Utils {

    public static final String S3_PREFIX = "https://onthemars-dev.s3.ap-northeast-2.amazonaws.com";
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Optional<String> upload(MultipartFile file, String name, S3Dir dir) {
        try {
            FileUtils.validImgFile(file.getInputStream());

            log.info("S3 업로드 시작");
            String filename =
                dir.getPath() + "/" + name + "_" + file.getOriginalFilename();

            ObjectMetadata objMetaData = new ObjectMetadata();
            objMetaData.setContentLength(file.getInputStream().available());
            amazonS3Client.putObject(
                new PutObjectRequest(bucket, filename, file.getInputStream(), objMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            String path = amazonS3Client.getUrl(bucket, filename).getPath();
            return Optional.of(path);
        } catch(IOException | NullPointerException e){
            log.warn(e.getMessage());
        }

        return Optional.empty();
    }

    public void delete(String profileImgUrl) {
        log.info("File Delete : " + profileImgUrl);
        amazonS3Client.deleteObject(bucket, profileImgUrl);
    }

    public Optional<String> get(String filename){
        String path = amazonS3Client.getUrl(bucket, filename).getPath();
        return Optional.of(path);
    }
}
