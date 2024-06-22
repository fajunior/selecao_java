package br.com.pitang.selecao_java.controllers;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pitang.selecao_java.entities.Car;
import br.com.pitang.selecao_java.entities.User;
import br.com.pitang.selecao_java.repositories.CarRepository;
import br.com.pitang.selecao_java.services.UserService;
import br.com.pitang.selecao_java.vo.ErrorMessageVO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/cars")
public class CarController {

	@Autowired
	private UserService userService;

	@Autowired
	private CarRepository carRepository;
	
	private int userId = 1;

	@RequestMapping(method = { RequestMethod.POST })
	public Car createCar(@RequestBody Car car) {
		User user = userService.findById(userId).get();
		car.setUser(user);
		carRepository.save(car);
		System.out.println(user);
		System.out.println(car);
		return car;
	}
	
	@RequestMapping(method = {RequestMethod.GET})
	public Optional<Iterable<Car>> list() {
		Optional<Iterable<Car>> list = carRepository.findByUserId(userId);
		return list;
	}

	@GetMapping(path = "/{id}")
	public Optional<Car> findById(@PathVariable int id) {
		Optional<Car> car = carRepository.findByIdAndUserId(id, userId);
		return car;
	}

	@DeleteMapping(path = "/{id}")
	public void remove(@PathVariable int id) {
		carRepository.deleteById(id);
	}

	@PutMapping(path = "/{id}")
	public Car updateCar(@PathVariable int id,@RequestBody  Car car) {
		if (carRepository.existsById(id)) {
			
			carRepository.save(car);
		}
		return car;
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
	    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
	    for (ConstraintViolation<?> violation : violations) {
            // Get the annotation type of the constraint violation
            Class<? extends Annotation> violationAnnotationType = violation.getConstraintDescriptor().getAnnotation().annotationType();

            // Define the specific annotation type you're comparing against
            Class<? extends Annotation> notNullAnnotationType = NotNull.class;
            Class<? extends Annotation> notBlankAnnotationType = NotBlank.class;

            if (notNullAnnotationType.equals(violationAnnotationType) || notBlankAnnotationType.equals(violationAnnotationType)) {
            	ErrorMessageVO vo = new ErrorMessageVO("Missing fields",5);
    			ex.printStackTrace();
    			return new ResponseEntity<>(vo, HttpStatus.BAD_REQUEST);
            }
        }
	    
	    List<String> violationMessages = violations.stream()
	           .map(violation -> String.format("%s value '%s' %s",
	                    violation.getPropertyPath().toString(), 
	                    //violation.getInvalidValue(), violation.getMessage()))
	                    violation.getInvalidValue(), violation.getMessageTemplate()))
	           .collect(Collectors.toList());


	    System.out.println(violationMessages);

	    return new ResponseEntity<>(violationMessages, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		ErrorMessageVO vo = new ErrorMessageVO("License plate already exists",3);
		ex.printStackTrace();
		return new ResponseEntity<>(vo, HttpStatus.BAD_REQUEST);
	}
}
