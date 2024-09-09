package com.tcs.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.tcs.entity.TblPersona;


@Repository
public interface TblPersonaRepository extends JpaRepository<TblPersona,Long>,JpaSpecificationExecutor<TblPersona> {
	
	List<TblPersona> findAllByEstadoTrue();
	
	Optional<TblPersona> findByIdAndEstadoTrue(Long id);
	
}
