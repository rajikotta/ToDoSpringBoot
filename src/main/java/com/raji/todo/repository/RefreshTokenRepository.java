package com.raji.todo.repository;

import com.raji.todo.models.db.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    void deleteByUserIdAndHashedToken(Long userId,String hashedToken);
}
