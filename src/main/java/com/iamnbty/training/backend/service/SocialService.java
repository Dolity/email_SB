package com.iamnbty.training.backend.service;

import com.iamnbty.training.backend.entity.Social;
import com.iamnbty.training.backend.entity.User;
import com.iamnbty.training.backend.exception.BaseException;
import com.iamnbty.training.backend.exception.UserException;
import com.iamnbty.training.backend.repository.SocialRepository;
import com.iamnbty.training.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SocialService {

    public final SocialRepository repository;

    public SocialService(SocialRepository repository) {
        this.repository = repository;
    }

    public Optional <Social> findByUser(User user) throws BaseException {
        return repository.findByUser(user);
    }

    public Social create(User user, String facebook, String line, String instagram, String tiktok) {

        // Create
        Social  entity = new Social();

        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setLine(line);
        entity.setInstagram(instagram);
        entity.setTiktok(tiktok);

        return repository.save(entity);
    }

}
