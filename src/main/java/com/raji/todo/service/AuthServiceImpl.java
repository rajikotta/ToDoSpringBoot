package com.raji.todo.service;

import com.raji.todo.models.db.RefreshTokenEntity;
import com.raji.todo.models.db.UserEntity;
import com.raji.todo.models.dto.SignupRequestDto;
import com.raji.todo.models.dto.LoginRequestDto;
import com.raji.todo.models.dto.TokenPairDto;
import com.raji.todo.repository.RefreshTokenRepository;
import com.raji.todo.repository.UserRepository;
import com.raji.todo.security.HashEncoder;
import com.raji.todo.security.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final HashEncoder hashEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthServiceImpl(UserRepository userRepository, ModelMapper modelMapper, HashEncoder hashEncoder, JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.hashEncoder = hashEncoder;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void creteAccount(SignupRequestDto authRequestDto) {

        UserEntity _user = userRepository.findByUsername(authRequestDto.getUsername());
        if (_user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User already exists");
        }
        UserEntity user = modelMapper.map(authRequestDto, UserEntity.class);
        user.setHashedPassword(hashEncoder.encode(authRequestDto.getPassword()));
        userRepository.save(user);

    }

    @Override
    public TokenPairDto login(LoginRequestDto loginRequestDto)  {
        UserEntity _user = userRepository.findByUsername(loginRequestDto.getUsername());
        if (_user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        if (!hashEncoder.matches(loginRequestDto.getPassword(), _user.getHashedPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid credentials");
        }
        String accessToken = jwtService.generateAccessToken(_user.getId().toString());
        String refreshToken = jwtService.generateRefreshToken(_user.getId().toString());
        storeRefreshToken(_user.getId(), refreshToken);
        return new TokenPairDto(accessToken, refreshToken);
    }

    private void storeRefreshToken(Long userId, String rawRefreshToken) {

        String hashedToken = getHashedToken(rawRefreshToken);
        long refreshTokenValidityMs = 30L * 60L * 1000L;

        Instant expiresAt = Instant.now().plusMillis(refreshTokenValidityMs);
        RefreshTokenEntity refreshToken = new RefreshTokenEntity(userId, expiresAt, hashedToken, Instant.now());
        refreshTokenRepository.save(refreshToken);
    }

    private String getHashedToken(String rawRefreshToken) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(rawRefreshToken.getBytes());
        String hashedToken = Base64.getEncoder().encodeToString(hash);
        return hashedToken;
    }

    @Override
    public TokenPairDto refresh(TokenPairDto tokenPairDto) {
        if (!jwtService.validateRefreshToken(tokenPairDto.getRefreshToken())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token.");
        }
        String userId = jwtService.getUserIdFromToken(tokenPairDto.getRefreshToken());

        UserEntity userEntity = userRepository.findById(Long.getLong(userId))

                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid refresh token."));


        String hashedtoken = getHashedToken(tokenPairDto.getRefreshToken());
        refreshTokenRepository.deleteByUserIdAndHashedToken(userEntity.getId(), hashedtoken);

        String accessToken = jwtService.generateAccessToken(userEntity.getId().toString());
        String newRefreshToken = jwtService.generateRefreshToken(userEntity.getId().toString());
        storeRefreshToken(userEntity.getId(), newRefreshToken);
        return new TokenPairDto(accessToken, newRefreshToken);

    }

    @Override
    public UserEntity getUser(String userId) {
        return userRepository.findById(Long.parseLong(userId)).orElseThrow();
    }
}


