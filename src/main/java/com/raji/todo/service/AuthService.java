package com.raji.todo.service;

import com.raji.todo.models.db.UserEntity;
import com.raji.todo.models.dto.SignupRequestDto;
import com.raji.todo.models.dto.LoginRequestDto;
import com.raji.todo.models.dto.TokenPairDto;

public interface AuthService {
    void creteAccount(SignupRequestDto authRequestDto);

    TokenPairDto login(LoginRequestDto authRequestDto) ;

    TokenPairDto refresh(TokenPairDto refreshToken);

    UserEntity getUser(String userId);
}
