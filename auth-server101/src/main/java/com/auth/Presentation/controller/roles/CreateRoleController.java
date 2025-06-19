package com.auth.Presentation.controller.roles;


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
import com.auth.entity.Role;
import com.auth.infrastructure.repository.RoleRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/role")
public class CreateRoleController {

    @Inject
    RoleRepository roleRepository;

    @POST
    @Tags(value = @Tag(name="Roles", description="Role Manages"))
    @WithTransaction
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createRole(@Valid Role role) {
        return roleRepository.getByNameAsync(role.getName())
                .flatMap(existingRole -> {
                    if (existingRole != null) return Uni.createFrom().item(
                            Response.status(Response.Status.CONFLICT)
                                    .entity(new StatusMessage<Role>("Name already exists"))
                                    .build()
                    );
                    return roleRepository.persist(role)
                            .map(v -> Response.status(Response.Status.CREATED)
                                    .entity(new StatusMessage<>(role))
                                    .build())
                            .onFailure().recoverWithItem(
                                    Response.status(Response.Status.BAD_REQUEST)
                                            .entity(new StatusMessage<Role>("Invalid Request"))
                                            .build()
                            );
                });
    }
}
