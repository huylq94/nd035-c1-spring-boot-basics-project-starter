package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationFacade;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile
            , Model model){
        if (!multipartFile.isEmpty()) {
            List<Files> filesList = null;
            Authentication authentication = authenticationFacade.getAuthentication();
            User user = userService.getUser(authentication.getName());
            Integer userId = user.getUserId();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            Files file = fileService.getFilesByUserId(userId).stream()
                    .filter(s -> s.getFileName().equals(fileName))
                    .findFirst()
                    .orElse(null);
            //Check duplicate file
            if (Objects.nonNull(file)) {
                model.addAttribute("result", "error");
                model.addAttribute("message", "You have tried to add a duplicate file.");
            } else {
                String contentType = multipartFile.getContentType();
                String fileSize = String.valueOf(multipartFile.getSize());
                byte[] fileData = new byte[0];
                try {
                    fileData = multipartFile.getBytes();
                } catch (IOException e) {
                    model.addAttribute("result", "error");
                    model.addAttribute("message", "You have tried to add a file size large than 1mb.");
                    e.printStackTrace();
                }
                fileService.insertFile(Files.builder()
                        .fileName(fileName)
                        .contentType(contentType)
                        .fileSize(fileSize)
                        .userId(userId)
                        .fileData(fileData)
                        .build());
                model.addAttribute("result", "success");
            }
            model.addAttribute("files", fileService.getFilesByUserId(userId));
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "Please choose a file to upload");
        }
        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {
        boolean result = fileService.deleteFile(fileId);
        model.addAttribute("result", result ? "success" : "error");
        return "result";
    }

    @GetMapping(value = "/download-file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile(@PathVariable String fileName) {
        return fileService.getFileByFileName(fileName).getFileData();
    }
}
