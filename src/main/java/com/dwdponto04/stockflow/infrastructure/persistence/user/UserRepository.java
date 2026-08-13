package com.dwdponto04.stockflow.infrastructure.persistence.user;

import com.dwdponto04.stockflow.business.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long>{
}
