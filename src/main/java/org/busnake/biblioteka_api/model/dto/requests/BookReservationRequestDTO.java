package org.busnake.biblioteka_api.model.dto.requests;

import java.time.LocalDate;

public record BookReservationRequestDTO(LocalDate loanStartDate, LocalDate dueDate){}
