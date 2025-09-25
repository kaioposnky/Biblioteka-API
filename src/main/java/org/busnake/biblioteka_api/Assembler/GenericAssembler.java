package org.busnake.biblioteka_api.Assembler;

import org.busnake.biblioteka_api.Controller.RESTfulController;
import org.busnake.biblioteka_api.Model.Entities.Identifiable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class GenericAssembler<E extends Identifiable> implements RepresentationModelAssembler<E, EntityModel<E>> {
    private final RESTfulController controller;

    public GenericAssembler(RESTfulController controller) {
        this.controller = controller;
    }


    public CollectionModel<EntityModel<E>> toListModel(List<E> entities) {

        List<EntityModel<E>> entityModels = entities.stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(entityModels, linkTo(methodOn(controller.getClass()).all()).withSelfRel());
    }

    @Override
    public EntityModel<E> toModel(E entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(controller.getClass()).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(controller.getClass()).all()).withRel("books")
        );
    }
}
