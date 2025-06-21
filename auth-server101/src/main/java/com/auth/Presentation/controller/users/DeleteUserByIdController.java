package com.auth.Presentation.controller.users;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import com.auth.dto.StatusMessage;
import com.auth.entity.User;
import com.auth.infrastructure.repository.UserRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/user")
public class DeleteUserByIdController {
    @Inject
    UserRepository userRepository;

    @DELETE
    @Tags(value = @Tag(name="Users", description="User Operations"))
    @Path("/{id}")
    @WithTransaction
    public Uni<Response> deleteUser(@PathParam("id") Long id) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    if (user == null) {
                        return Uni.createFrom().item(
                                Response.status(Response.Status.NOT_FOUND)
                                        .entity(new StatusMessage<User>("User not found"))
                                        .build()
                        );
                    }
                    return userRepository.deleteById(id)
                            .map(v -> Response.ok(new StatusMessage<>(true)).build())
                            .onFailure().recoverWithItem(
                                    Response.status(Response.Status.BAD_REQUEST)
                                            .entity(new StatusMessage<User>("Invalid Request"))
                                            .build()
                            );
                });

    }
}