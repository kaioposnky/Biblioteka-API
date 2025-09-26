package org.busnake.biblioteka_api.model.dto.responses;

public interface ResponseDTO<E,R> {
    R fromEntity(E entity);
}
