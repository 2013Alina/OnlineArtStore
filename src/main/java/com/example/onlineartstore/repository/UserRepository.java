package com.example.onlineartstore.repository;

import com.example.onlineartstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
