package br.com.pitang.selecao_java.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.pitang.selecao_java.entities.Car;

public interface CarRepository extends CrudRepository<Car, Integer> {
	@Query
	public Optional<Iterable<Car>> findByUserId(int userId);
	
	@Query
	public Optional<Car> findByIdAndUserId(int id, int userId);
	
}
