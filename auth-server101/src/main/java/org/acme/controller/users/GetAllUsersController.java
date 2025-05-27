package org.acme.controller.users;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.StatusMessage;
import org.acme.entity.User;
import org.acme.repository.UserRepository;

import java.util.List;

@Path("/user")
public class GetAllUsersController {

    @Inject
    UserRepository userRepository;

    @GET
    @WithTransaction
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getAllUsers() {
        return userRepository.listAll()
                .map(users -> Response.ok(new StatusMessage<>(users)).build())
                .onFailure().recoverWithItem(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new StatusMessage<User>("Invalid Request"))
                            .build()
                );
    }
}