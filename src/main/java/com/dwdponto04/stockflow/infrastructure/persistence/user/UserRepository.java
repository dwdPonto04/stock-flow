package com.dwdponto04.stockflow.infrastructure.persistence.user;

import com.dwdponto04.stockflow.business.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User,Long>{

    boolean existsByEmail(String email);
    
    Optional <User> findByEmail(String email);

}
