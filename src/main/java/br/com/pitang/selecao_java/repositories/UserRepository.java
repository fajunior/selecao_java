package br.com.pitang.selecao_java.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.pitang.selecao_java.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query
	public Optional<User> findByEmail(String email);

	@Query
	public Optional<User> findByLogin(String login);
}
