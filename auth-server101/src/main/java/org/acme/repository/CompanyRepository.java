package org.acme.repository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Company;


@ApplicationScoped
public class CompanyRepository extends EntityRepositoryBase<Company, Long>{

    public Uni<Company> getByEmailAsync(String email) {
        return find("email", email).firstResult();
    }
}
