package com.iamnbty.training.backend.business;

import com.dlt.backendKafka.common.EmailRequest;
import com.iamnbty.training.backend.entity.User;
import com.iamnbty.training.backend.exception.BaseException;
import com.iamnbty.training.backend.exception.FileException;
import com.iamnbty.training.backend.exception.UserException;
import com.iamnbty.training.backend.mapper.UserMapper;
import com.iamnbty.training.backend.model.MLoginRequest;
import com.iamnbty.training.backend.model.MLoginResponse;
import com.iamnbty.training.backend.model.MRegisterRequest;
import com.iamnbty.training.backend.model.MRegisterResponse;
import com.iamnbty.training.backend.service.TokenService;
import com.iamnbty.training.backend.service.UserService;
import com.iamnbty.training.backend.util.SecurityUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserBusiness {

    private  final UserService userService;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    private final EmailBusiness emailBusiness;

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper, EmailBusiness emailBusiness) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.emailBusiness = emailBusiness;
    }

    public MLoginResponse login(MLoginRequest request) throws BaseException {
        // Validate request

        // Verify database
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            throw  UserException.LoginFailEmailNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(request.getPassword(), user.getPassword())) {
            throw  UserException.LoginFailEmailPasswordIncorrect();
        }

        MLoginResponse response = new MLoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();


        Optional<User> optUser = userService.findById(userId);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return  tokenService.tokenize(user);
    }

    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName(), token);

        sendEmail(user);

        return userMapper.toRegisterResponse(user);
    }

    private void sendEmail(User user) {

        String token = user.getToken();

        try {
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        }catch (BaseException e) {
            e.printStackTrace();
        }

    }

    public String uploadProfilePicture(MultipartFile file) throws BaseException {
        if (file == null) {
            throw FileException.fileNull();
        }

        if (file .getSize() > 1048576 * 2) {
            throw  FileException.fileMaxSize();
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            throw FileException.unsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supportedTypes.contains(contentType)) {
            throw FileException.unsupported();
        }

        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }


}
