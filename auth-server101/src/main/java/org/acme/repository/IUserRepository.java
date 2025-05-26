package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import org.acme.entity.User;

import java.util.List;

public interface IUserRepository extends PanacheRepository<User> {
    Uni<User> GetByIdAsync(Long id);
    Uni<User> GetByEmailAsync(String email);
    Uni<List<User>> GetAllUserAsync();
}
