package com.iamnbty.training.backend.service;

import com.iamnbty.training.backend.entity.User;
import com.iamnbty.training.backend.exception.BaseException;
import com.iamnbty.training.backend.exception.UserException;
import com.iamnbty.training.backend.repository.UserRepository;
import com.iamnbty.training.backend.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    public final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional <User> findById(String id) {
        return repository.findById(id);
    }

    public Optional <User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

//    public User update(User user) {
//        return repository.save(user);
//    }

    public User updateName(String id, String name) throws BaseException {
        Optional<User> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);

        return repository.save(user);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public boolean matchPassword(String rawPassword, String encodeedPassword) {
        return passwordEncoder.matches(rawPassword, encodeedPassword);
    }

    public User create(String email, String password, String name, String token) throws BaseException {
        // Validate
        if (Objects.isNull(email)) {
            throw UserException.CreateEmailNull();
        }

        if (Objects.isNull(password)) {
            throw UserException.CreatePasswordNull();
        }

        if (Objects.isNull(name)) {
            throw UserException.CreateNameNull();
        }

        // Verify
        if (repository.existsByEmail(email)) {
            throw UserException.CreateEmailDuplicated();
        }

        // Save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);
        entity.setToken(token);
        entity.setTokenExpire(nextXMinute(30));

        return repository.save(entity);
    }

    private Date nextXMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }
}
