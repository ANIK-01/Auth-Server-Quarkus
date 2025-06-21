package com.auth.infrastructure.repository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import com.auth.entity.User;


@ApplicationScoped
public class UserRepository extends EntityRepositoryBase<User, Long>{

    public Uni<User> getByEmailAsync(String email) {
        return find("Email", email).firstResult();
    }
}
