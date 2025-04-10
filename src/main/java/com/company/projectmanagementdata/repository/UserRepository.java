package com.company.projectmanagementdata.repository;

import com.company.projectmanagementdata.entity.User;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JmixDataRepository<User, UUID>{
}