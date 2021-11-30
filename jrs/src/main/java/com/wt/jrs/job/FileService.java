package com.wt.jrs.job;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileService {

    @Autowired
    ServletContext context;

    public String IMAGE_RESOURCE_PATH = "/images/jobId";
    MultipartFile image;

    public void uploadImage(Optional<MultipartFile> imageOptional, Long jobId) throws IOException {

        String uploadPath = context.getRealPath(IMAGE_RESOURCE_PATH + "/" + jobId);
        File directory = new File(uploadPath);

        if(!directory.exists()) {
            if (!directory.mkdirs())
                return;
        }

        int nrFiles = Objects.requireNonNull(directory.list()).length;

        if (imageOptional.isPresent()) {
            this.image = imageOptional.get();
            try {
                Path copyLocation = Paths
                        .get(uploadPath + File.separator + StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename())));
                if (nrFiles != 0) {
                    FileUtils.cleanDirectory(directory);
                }
                Files.copy(image.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not store file " + image.getOriginalFilename());
            }
        } else { // default image
            try {
                InputStream defaultImage = new FileInputStream(context.getRealPath("/images/default/default-job-logo.png"));
                Path copyLocation = Paths
                        .get(uploadPath + File.separator + StringUtils.cleanPath("default-job-logo.png"));
                if (nrFiles != 0) {
                    FileUtils.cleanDirectory(directory);
                }
                Files.copy(defaultImage, copyLocation, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Could not store file");
            }
        }
    }

    public void deleteImage(Long jobId){
        String imagePath = context.getRealPath(IMAGE_RESOURCE_PATH + "/" + jobId);
        File directory = new File(imagePath);
        if (directory.exists()){
            try {
                FileUtils.deleteDirectory(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getJobImage(Long jobId) {
        String image = null;
        String imagePath = context.getRealPath(IMAGE_RESOURCE_PATH + "/" + jobId);
        File imageDirectory = new File(imagePath);
        File[] imageDirectoryContent = imageDirectory.listFiles();
        if (imageDirectoryContent != null){
            if (imageDirectoryContent.length == 1){
                File imageFile = imageDirectoryContent[0];
                String encodeBase64 = null;
                try {
                    String extension = FilenameUtils.getExtension(imageFile.getName());
                    FileInputStream fileInputStream = new FileInputStream(imageFile);
                    byte [] bytes = new byte[(int) imageFile.length()];
                    fileInputStream.read(bytes);
                    encodeBase64 = Base64.getEncoder().encodeToString(bytes);
                    image = "data:image/" + extension + ";base64," + encodeBase64;
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }

    public String getImageName(Long jobId) {
        String imagePath = context.getRealPath(IMAGE_RESOURCE_PATH + "/" + jobId);
        File imageDirectory = new File(imagePath);
        File[] imageDirectoryContent = imageDirectory.listFiles();
        if (imageDirectoryContent != null) {
            if (imageDirectoryContent.length != 1) {
                return "unknown";
            }
            return imageDirectoryContent[0].getName();
        }
        return "unknown";
    }


}
