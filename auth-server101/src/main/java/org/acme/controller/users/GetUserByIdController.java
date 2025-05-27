package org.acme.controller.users;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.StatusMessage;
import org.acme.entity.User;
import org.acme.repository.UserRepository;

@Path("/user")
public class GetUserByIdController {

    @Inject
    UserRepository userRepository;

    @GET
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