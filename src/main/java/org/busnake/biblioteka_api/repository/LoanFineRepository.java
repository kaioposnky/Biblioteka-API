package org.busnake.biblioteka_api.repository;

import org.busnake.biblioteka_api.model.entities.LoanFine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanFineRepository extends JpaRepository<LoanFine, Long> {

}
