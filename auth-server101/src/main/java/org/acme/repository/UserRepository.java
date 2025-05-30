package org.acme.repository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.User;


@ApplicationScoped
public class UserRepository extends EntityRepositoryBase<User, Long>{

    public Uni<User> getByEmailAsync(String email) {
        return find("email", email).firstResult();
    }
}
