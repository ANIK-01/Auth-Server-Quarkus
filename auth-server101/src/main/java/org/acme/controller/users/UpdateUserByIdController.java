package org.acme.controller.users;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.StatusMessage;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/user")
public class UpdateUserByIdController {

    @Inject
    UserRepository userRepository;

    @PUT
    @Tags(value = @Tag(name="Users", description="User Operations"))
    @Path("/{id}")
    @WithTransaction
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateUserById(@PathParam("id") Long id, @Valid User updatedUser) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    if (existingUser == null) {
                        return Uni.createFrom().item(
                                Response.status(Response.Status.NOT_FOUND)
                                        .entity(new StatusMessage<User>("User not found"))
                                        .build()
                        );
                    }
                    // Check if the updated email is taken by another user
                    return userRepository.getByEmailAsync(updatedUser.getEmail())
                            .flatMap(userWithEmail -> {
                                if (userWithEmail != null && !userWithEmail.getId().equals(id)) {
                                    return Uni.createFrom().item(
                                            Response.status(Response.Status.CONFLICT)
                                                    .entity(new StatusMessage<User>("Email already exists"))
                                                    .build()
                                    );
                                }
                                // Update fields
                                existingUser.setName(updatedUser.getName());
                                existingUser.setEmail(updatedUser.getEmail());
                                existingUser.setPassword(updatedUser.getPassword());
                                existingUser.setGender(updatedUser.getGender());
                                existingUser.setAge(updatedUser.getAge());

                                return userRepository.persist(existingUser)
                                        .map(v -> Response.ok(new StatusMessage<>(existingUser)).build())
                                        .onFailure().recoverWithItem(
                                                Response.status(Response.Status.BAD_REQUEST)
                                                        .entity(new StatusMessage<User>("Failed to update user"))
                                                        .build()
                                        );
                            });
                });
    }
}