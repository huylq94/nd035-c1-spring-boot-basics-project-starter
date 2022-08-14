package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<Files> getFilesByUserId(Integer userId) {
        List<Files> filesList = fileMapper.getFilesByUserId(userId);
        return filesList;
    }

    public int insertFile(Files file) {

        return fileMapper.insert(file);
    }

    public Files getFileByFileId(Integer fileId) {

        return fileMapper.getFileByFileId(fileId);
    }

    public boolean deleteFile(Integer fileId) {

        return fileMapper.deleteFile(fileId);
    }

    public Files getFileByFileName(String fileName) {

        return fileMapper.getFileByFileName(fileName);
    }
}
