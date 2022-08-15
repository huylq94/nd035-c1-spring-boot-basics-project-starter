package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    @Autowired
    private CredentialMapper credentialMapper;

    public List<Credentials> getCredentialsByUserId(Integer userId) {

        return credentialMapper.findCredentialsByUserId(userId);
    }

    public void insert(Credentials credentials) {
        credentialMapper.insert(credentials);

    }

    public void update(Credentials credentials) {

        credentialMapper.update(credentials);
    }

    public void delete(Integer credentialId) {

        credentialMapper.delete(credentialId);
    }
}
