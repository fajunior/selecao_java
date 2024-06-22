package br.com.pitang.selecao_java.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pitang.selecao_java.entities.Car;
import br.com.pitang.selecao_java.entities.User;
import br.com.pitang.selecao_java.exceptions.FieldValueExistisException;
import br.com.pitang.selecao_java.exceptions.NotNullableAttributeException;
import br.com.pitang.selecao_java.repositories.CarRepository;
import br.com.pitang.selecao_java.repositories.UserRepository;
import jakarta.transaction.Transactional;



@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	private Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	public User createUser(User user) throws FieldValueExistisException {
		this.validateUserData(user);

		userRepository.save(user);
		
		if (user.getCars() != null) {
			for (Car car : user.getCars()) {
				car.setUser(user); // Link the car back to the user
				carRepository.save(car);
			}
		}
		
		return user;
	}

	public Iterable<User> list() {
		return userRepository.findAll();
	}
/*
	public Optional<User> findById(int id) {
		Optional<User> optional = userRepository.findById(id);
		
		if (optional.isPresent()) {
			
			Optional<Iterable<Car>> cars = carRepository.findByUserId(id);
			if (cars.isPresent()) {
				optional.get().setCars((List<Car>)cars.get());
			}
		}
		
		return optional;
	}
	*/
	@Transactional
	public Optional<User> findById(int id) {
		Optional<User> optional = userRepository.findById(id);
		return optional;
	}
	
	public Optional<User> findByLogin(String login) {
		return userRepository.findByLogin(login);
	}


	public void remove(int id) {
		userRepository.deleteById(id);
	}

	public User updateUser(int id, User user) {
		if (userRepository.existsById(id)) {
			User savedUser = userRepository.findById(id).get();
			user.setCars(savedUser.getCars());
			userRepository.save(user);
		}
		return user;
	}

	private void validateUserData(User user) {
		// logs a debug message
		if (LOGGER.isDebugEnabled()) {
			LOGGER.info(String.format("User: %s", user));
		}
		
		this.validateMandatoryFields(user);
		this.validateFieldValuesExistis(user);
		//TODO create validation Campos inválidos
		
		
	}

	private void validateFieldValuesExistis(User user) {
		Optional<User> savedUser = userRepository.findByEmail(user.getEmail());
		if (savedUser.isPresent()) {
			if (user.getId() == 0 && user.getId() != (savedUser.get()).getId()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.info(String.format("Saved User: %s", savedUser.get()));
				}
				throw new FieldValueExistisException("Email", 12);
			}
		}


		savedUser = userRepository.findByLogin(user.getLogin());
		if (savedUser.isPresent()) {
			if (user.getId() == 0 && user.getId() != (savedUser.get()).getId()) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.info(String.format("Saved User: %s", savedUser.get()));
				}
				throw new FieldValueExistisException("Login", 13);
			}
		}
	}
	
	private void validateMandatoryFields(User user) {
		if (user.getFirstName() == null || user.getFirstName().isBlank()) {
			throw new NotNullableAttributeException("firstName", 15);
		}
		
		if (user.getLastName() == null || user.getLastName().isBlank()) {
			throw new NotNullableAttributeException("lastName", 15);
		}
		
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new NotNullableAttributeException("email", 15);
		}
		
		if (user.getLogin()==null || user.getLogin().isBlank()) {
			throw new NotNullableAttributeException("login", 15);
		}
		
		if (user.getPassword() == null || user.getPassword().isBlank()) {
			throw new NotNullableAttributeException("password", 15);
		}
		
		if (user.getPhone() == null || user.getPhone().isBlank()) {
			throw new NotNullableAttributeException("phone", 15);
		}
	}

}