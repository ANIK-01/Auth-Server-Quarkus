package org.acme.controller.compaines;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.StatusMessage;
import org.acme.entity.Company;
import org.acme.repository.CompanyRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/company")
public class GetAllCompaniesController {

    @Inject
    CompanyRepository companyRepository;

    @GET
    @Tags(value = @Tag(name="Companies", description="Company Operations"))
    @WithTransaction
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllCompanies() {
        return companyRepository.listAll()
                .map(companies -> Response.ok(new StatusMessage<>(companies)).build())
                .onFailure().recoverWithItem(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new StatusMessage<Company>("Invalid Request"))
                            .build()
                );
    }
}