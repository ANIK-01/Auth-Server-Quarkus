package org.acme.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import java.util.List;


public abstract class EntityRepositoryBase<T, ID> implements PanacheRepositoryBase<T, ID> {

    public Uni<List<T>> getAllAsync() {
        return listAll();
    }

    public Uni<T> getByIdAsync(ID id) {
        return findById(id);
    }

    public Uni<T> getByNameAsync(String name){
        return find("name", name).firstResult();
    }
}

