package com.auth.Presentation.controller.compaines;


import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.auth.dto.StatusMessage;
import com.auth.entity.Company;
import com.auth.infrastructure.repository.CompanyRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/company")
public class CreateCompanyController {

    @Inject
    CompanyRepository companyRepository;

    @POST
    @Tags(value = @Tag(name="Companies", description="Company Operations"))
    @WithTransaction
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public Uni<Response> createCompany(@Valid Company company) {
        return companyRepository.getByNameAsync(company.getName())
                .flatMap(existingCompany -> {
                    if (existingCompany != null) return Uni.createFrom().item(
                            Response.status(Response.Status.CONFLICT)
                                    .entity(new StatusMessage<Company>("Name already exists"))
                                    .build()
                    );

                    return companyRepository.getByEmailAsync(company.getCompanyEmail())
                            .flatMap(existEmail -> {
                                if (existEmail != null) return Uni.createFrom().item(
                                        Response.status(Response.Status.CONFLICT)
                                                .entity(new StatusMessage<Company>("Company Email already exists"))
                                                .build()
                                );

                                return companyRepository.persist(company)
                                        .map(v -> Response.status(Response.Status.CREATED)
                                                .entity(new StatusMessage<>(company))
                                                .build())
                                        .onFailure().recoverWithItem(
                                                Response.status(Response.Status.BAD_REQUEST)
                                                        .entity(new StatusMessage<Company>("Invalid Request"))
                                                        .build()
                                        );
                            });

                });
    }
}
