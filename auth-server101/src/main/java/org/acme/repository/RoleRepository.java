package org.acme.repository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Role;

@ApplicationScoped
public class RoleRepository extends EntityRepositoryBase<Role, Long>{
}