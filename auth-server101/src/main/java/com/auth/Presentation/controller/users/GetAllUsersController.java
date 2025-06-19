package com.auth.Presentation.controller.users;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.auth.dto.StatusMessage;
import com.auth.entity.User;
import com.auth.infrastructure.repository.UserRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/user")
public class GetAllUsersController {

    @Inject
    UserRepository userRepository;

    @GET
    @Tags(value = @Tag(name="Users", description="User Operations"))
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