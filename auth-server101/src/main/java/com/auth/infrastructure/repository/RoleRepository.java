package com.auth.infrastructure.repository;

import jakarta.enterprise.context.ApplicationScoped;
import com.auth.entity.Role;

@ApplicationScoped
public class RoleRepository extends EntityRepositoryBase<Role, Long>{
}