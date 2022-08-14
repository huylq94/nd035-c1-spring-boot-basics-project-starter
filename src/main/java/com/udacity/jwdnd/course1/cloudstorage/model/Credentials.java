package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Credentials {
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;
}