package com.auth.Presentation.controller.compaines;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.auth.dto.StatusMessage;
import com.auth.entity.Company;
import com.auth.infrastructure.repository.CompanyRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/company")
public class GetCompanyByIdController {

    @Inject
    CompanyRepository companyRepository;

    @GET
    @Tags(value = @Tag(name="Companies", description="Company Operations"))
    @Path("/{id}")
    @WithTransaction
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getCompanyById(@PathParam("id") Long id) {
        return companyRepository.findById(id)
                .flatMap(company -> {
                    if (company == null) {
                        return Uni.createFrom().item(
                                Response.status(Response.Status.NOT_FOUND)
                                        .entity(new StatusMessage<Company>("Company not found"))
                                        .build()
                        );
                    }
                    return Uni.createFrom().item(
                            Response.ok(new StatusMessage<>(company)).build()
                    );
                });
    }

}