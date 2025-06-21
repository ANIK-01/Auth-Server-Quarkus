package com.auth.Presentation.controller.users;


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
import com.auth.entity.User;
import com.auth.infrastructure.repository.UserRepository;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

@Path("/user")
public class CreateUserController {

    @Inject
    UserRepository userRepository;

    @POST
    @Tags(value = @Tag(name="Users", description="User Operations"))
    @WithTransaction
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createUser(@Valid User user) {
        return userRepository.getByEmailAsync(user.getEmail())
                .flatMap(existingUser -> {
                    if (existingUser != null) return Uni.createFrom().item(
                            Response.status(Response.Status.CONFLICT)
                                    .entity(new StatusMessage<User>("Email already exists"))
                                    .build()
                    );
                    return userRepository.persist(user)
                            .map(v -> Response.status(Response.Status.CREATED)
                                    .entity(new StatusMessage<>(user))
                                    .build())
                            .onFailure().recoverWithItem(
                                    Response.status(Response.Status.BAD_REQUEST)
                                            .entity(new StatusMessage<User>("Invalid Request"))
                                            .build()
                            );
                });
    }
}
