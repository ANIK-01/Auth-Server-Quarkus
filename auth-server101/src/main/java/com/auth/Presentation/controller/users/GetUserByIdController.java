package com.auth.Presentation.controller.users;

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
import com.auth.entity.User;
import com.auth.infrastructure.repository.UserRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/user")
public class GetUserByIdController {

    @Inject
    UserRepository userRepository;

    @GET
    @Tags(value = @Tag(name="Users", description="User Operations"))
    @Path("/{id}")
    @WithTransaction
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getUserById(@PathParam("id") Long id) {
        return userRepository.findById(id)
                .flatMap(user -> {
                    if (user == null) {
                        return Uni.createFrom().item(
                                Response.status(Response.Status.NOT_FOUND)
                                        .entity(new StatusMessage<User>("User not found"))
                                        .build()
                        );
                    }
                    return Uni.createFrom().item(
                            Response.ok(new StatusMessage<>(user)).build()
                    );
                });
    }

}