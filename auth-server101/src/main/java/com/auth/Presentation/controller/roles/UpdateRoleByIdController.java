package com.auth.Presentation.controller.roles;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.auth.dto.StatusMessage;
import com.auth.entity.Role;
import com.auth.infrastructure.repository.RoleRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/role")
public class UpdateRoleByIdController {

    @Inject
    RoleRepository roleRepository;

    @PUT
    @Tags(value = @Tag(name="Roles", description="Role Manages"))
    @Path("/{id}")
    @WithTransaction
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateRoleById(@PathParam("id") Long id, @Valid Role updatedRole) {
        return roleRepository.findById(id)
                .flatMap(existingRole -> {
                    if (existingRole == null) {
                        return Uni.createFrom().item(
                                Response.status(Response.Status.NOT_FOUND)
                                        .entity(new StatusMessage<Role>("Role not found"))
                                        .build()
                        );
                    }
                    // Check if the updated email is taken by another role
                    return roleRepository.getByNameAsync(updatedRole.getName())
                            .flatMap(roleWithName -> {
                                if (roleWithName != null && !roleWithName.getId().equals(id)) {
                                    return Uni.createFrom().item(
                                            Response.status(Response.Status.CONFLICT)
                                                    .entity(new StatusMessage<Role>("Name already exists"))
                                                    .build()
                                    );
                                }
                                // Update fields
                                existingRole.setName(updatedRole.getName());
                                existingRole.setName(updatedRole.getName());


                                return roleRepository.persist(existingRole)
                                        .map(v -> Response.ok(new StatusMessage<>(existingRole)).build())
                                        .onFailure().recoverWithItem(
                                                Response.status(Response.Status.BAD_REQUEST)
                                                        .entity(new StatusMessage<Role>("Failed to update role"))
                                                        .build()
                                        );
                            });
                });
    }
}