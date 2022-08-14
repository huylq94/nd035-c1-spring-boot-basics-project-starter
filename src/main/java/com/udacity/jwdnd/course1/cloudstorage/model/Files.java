package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Files {
    private Integer fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;

    public String getBase64() {
        if (!StringUtils.isEmpty(this.fileData)) {
            return Base64.getEncoder().encodeToString(this.fileData);
        }
        return null;
    }
}
