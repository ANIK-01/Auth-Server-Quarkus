package com.auth.Presentation.controller.compaines;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.auth.dto.StatusMessage;
import com.auth.entity.Company;
import com.auth.infrastructure.repository.CompanyRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/company")
public class UpdateCompanyByIdController {

    @Inject
    CompanyRepository companyRepository;

    @PUT
    @Tags(value = @Tag(name="Companies", description="Company Operations"))
    @Path("/{id}")
    @WithTransaction
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateCompanyById(@PathParam("id") Long id, @Valid Company updatedCompany) {
        return companyRepository.findById(id)
                .flatMap(existingCompany -> {
                    if (existingCompany == null) {
                        return Uni.createFrom().item(
                                Response.status(Response.Status.NOT_FOUND)
                                        .entity(new StatusMessage<Company>("Company not found"))
                                        .build()
                        );
                    }
                    // Check if the updated email is taken by another company

                    return companyRepository.getByNameAsync(updatedCompany.getName())
                            .flatMap(existName -> {
                                if (existName != null && !existName.getId().equals(id)) return Uni.createFrom().item(
                                        Response.status(Response.Status.CONFLICT)
                                                .entity(new StatusMessage<Company>("Name already exists"))
                                                .build()
                                );
                                return companyRepository.getByEmailAsync(updatedCompany.getCompanyEmail())
                                        .flatMap(companyWithEmail -> {
                                            if (companyWithEmail != null && !companyWithEmail.getId().equals(id)) {
                                                return Uni.createFrom().item(
                                                        Response.status(Response.Status.CONFLICT)
                                                                .entity(new StatusMessage<Company>(" Company Email already exists"))
                                                                .build()
                                                );
                                            }
                                            // Update fields
                                            existingCompany.setName(updatedCompany.getName());
                                            existingCompany.setAddress(updatedCompany.getAddress());
                                            existingCompany.setCompanyEmail(updatedCompany.getCompanyEmail());
                                            existingCompany.setDescription(updatedCompany.getDescription());
                                            existingCompany.setIndustry(updatedCompany.getIndustry());

                                            return companyRepository.persist(existingCompany)
                                                    .map(v -> Response.ok(new StatusMessage<>(existingCompany)).build())
                                                    .onFailure().recoverWithItem(
                                                            Response.status(Response.Status.BAD_REQUEST)
                                                                    .entity(new StatusMessage<Company>("Failed to update company"))
                                                                    .build()
                                                    );
                                        });
                            });
                });
    }
}