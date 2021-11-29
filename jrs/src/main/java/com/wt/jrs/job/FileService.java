package com.wt.jrs.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {

    @Autowired
    ServletContext context;

    public String IMAGE_RESOURCE_PATH = "/images";

    public void uploadFile(MultipartFile file, Long jobId) {

        String uploadPath = context.getRealPath(IMAGE_RESOURCE_PATH + "/" + jobId);
        File directory = new File(uploadPath);

        if(!directory.exists()) {
            if (!directory.mkdirs())
                return;
        }

        try {
            Path copyLocation = Paths
                    .get(uploadPath + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("file copied successfully at " + copyLocation.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file " + file.getOriginalFilename());
        }
    }

}
