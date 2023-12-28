package com.gestionclientes2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gestionclientes2.model.Empleado;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, Long>, JpaRepository<Empleado, Long>{

}
