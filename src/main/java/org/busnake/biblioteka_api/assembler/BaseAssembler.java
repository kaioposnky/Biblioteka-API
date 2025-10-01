package org.busnake.biblioteka_api.assembler;

import org.busnake.biblioteka_api.controller.GenericController;
import org.busnake.biblioteka_api.model.entities.Identifiable;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BaseAssembler<E extends Identifiable, D> implements RepresentationModelAssembler<E, EntityModel<D>> {
    private final Class<? extends GenericController<E>> controllerClass;
    private final Function<E, D> toDtoConverter;

    @SuppressWarnings("unchecked")
    public BaseAssembler(Class<? extends GenericController<E>> controllerClass, Function<E, D> toDtoConverter) {
        this.controllerClass = controllerClass;
        this.toDtoConverter = toDtoConverter;
    }

    public CollectionModel<EntityModel<D>> toListModel(List<E> entities) {

        List<EntityModel<D>> entityModels = entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entityModels, linkTo(methodOn(controllerClass).all()).withSelfRel());
    }

    @Override
    public @NotNull EntityModel<D> toModel(@NotNull E entity) {

        D dto = toDtoConverter.apply(entity);

        return EntityModel.of(dto,
                linkTo(methodOn(controllerClass).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(controllerClass).all()).withRel(entity.getCollectionRel())
        );
    }
}
