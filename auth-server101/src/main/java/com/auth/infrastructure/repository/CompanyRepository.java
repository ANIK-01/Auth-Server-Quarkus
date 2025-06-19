package com.auth.infrastructure.repository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import com.auth.entity.Company;


@ApplicationScoped
public class CompanyRepository extends EntityRepositoryBase<Company, Long>{

    public Uni<Company> getByEmailAsync(String email) {
        return find("CompanyEmail", email).firstResult();
    }
}
