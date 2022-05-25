package com.example.sysventas_clientes_ms.model;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.sysventas_clientes_ms.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{
	
	@Transactional
	@Query("FROM Cliente c ORDER BY c.primerNombre, c.segundoNombre")
	public List<Cliente> findClientesOrderByName();
	
	@Transactional
	@Query("FROM Cliente c WHERE c.cedula = :cedula")
	public Optional<Cliente> findClienteByCode(String cedula);
	
	@Transactional
	@Query("FROM Cliente c  WHERE c.estado ='Activo' ORDER BY c.primerNombre, c.segundoNombre")
	public List<Cliente> findClientesActivos();
	@Transactional
	@Query("FROM Cliente c  WHERE c.estado ='Inactivo' ORDER BY c.primerNombre, c.segundoNombre")
	public List<Cliente> findClientesInactivos();
}
