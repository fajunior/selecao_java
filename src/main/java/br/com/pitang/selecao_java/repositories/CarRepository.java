package br.com.pitang.selecao_java.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.pitang.selecao_java.entities.Car;

public interface CarRepository extends JpaRepository<Car, Integer> {
	@Query
	public Optional<Iterable<Car>> findByUserId(int userId);
	
	@Query
	public Optional<Car> findByIdAndUserId(int id, int userId);
	
}
