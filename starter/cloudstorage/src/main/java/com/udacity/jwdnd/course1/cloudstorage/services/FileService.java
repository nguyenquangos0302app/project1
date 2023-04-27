package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileMapper fileMapper;

    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<String> findNameFilesByAuthentication(Authentication authentication) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        List<File> files = fileMapper.findByUserId(userId);

        if (CollectionUtils.isEmpty(files)) {
            return new ArrayList<>();
        }

        return files.stream().map(File::getFileName).collect(Collectors.toList());
    }

    public boolean checkDuplicatedFileName(Authentication authentication, String fileName) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        File file = fileMapper.findByUserIdAndFileName(userId, fileName);

        if (Objects.nonNull(file)) {
            return true;
        }

        return false;
    }

    public void save(Authentication authentication, MultipartFile multipartFile) throws IOException {
        InputStream fis = multipartFile.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] fileData = buffer.toByteArray();

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = userService.findUserIdByAuthentication(authentication);
        File file = new File(0, fileName, contentType, fileSize, userId, fileData);
        fileMapper.save(file);
    }

    public void deleteFileByAuthenticationAndFileName(Authentication authentication, String fileName) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        fileMapper.deleteFileByFileNameAndUserId(fileName, userId);
    }

    public byte[] findContentFileByAuthenticationAndFileName(Authentication authentication, String fileName) {
        Integer userId = userService.findUserIdByAuthentication(authentication);
        File file = fileMapper.findByUserIdAndFileName(userId, fileName);
        return file.getFileData();
    }

}
