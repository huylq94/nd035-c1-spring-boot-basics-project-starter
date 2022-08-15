package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CredentialForm {
    private String credentialId;
    private String url;
    private String key;
    private String password;
    private String username;
    private String userId;
}
