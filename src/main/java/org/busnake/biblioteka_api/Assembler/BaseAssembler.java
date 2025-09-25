package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.GenericController;
import org.busnake.biblioteka_api.Model.Entities.Identifiable;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BaseAssembler<E extends Identifiable> implements RepresentationModelAssembler<E, EntityModel<E>> {
    private final Class<? extends GenericController<E>> controllerClass;

    @SuppressWarnings("unchecked")
    public BaseAssembler(@Lazy Class<? extends GenericController<E>> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public CollectionModel<EntityModel<E>> toListModel(List<E> entities) {

        List<EntityModel<E>> entityModels = entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entityModels, linkTo(methodOn(controllerClass).all()).withSelfRel());
    }

    @Override
    public @NotNull EntityModel<E> toModel(@NotNull E entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(controllerClass).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(controllerClass).all()).withRel("books")
        );
    }
}
