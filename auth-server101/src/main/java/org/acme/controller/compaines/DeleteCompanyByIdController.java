package org.acme.controller.compaines;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.acme.dto.StatusMessage;
import org.acme.entity.Company;
import org.acme.repository.CompanyRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/company")
public class DeleteCompanyByIdController {
    @Inject
    CompanyRepository companyRepository;

    @DELETE
    @Tags(value = @Tag(name="Companies", description="Company Operations"))
    @Path("/{id}")
    @WithTransaction
    public Uni<Response> deleteCompany(@PathParam("id") Long id) {
        return companyRepository.findById(id)
                .flatMap(company -> {
                    if (company == null) {
                        return Uni.createFrom().item(
                                Response.status(Response.Status.NOT_FOUND)
                                        .entity(new StatusMessage<Company>("Company not found"))
                                        .build()
                        );
                    }
                    return companyRepository.deleteById(id)
                            .map(v -> Response.ok(new StatusMessage<>(true)).build())
                            .onFailure().recoverWithItem(
                                    Response.status(Response.Status.BAD_REQUEST)
                                            .entity(new StatusMessage<Company>("Invalid Request"))
                                            .build()
                            );
                });

    }
}