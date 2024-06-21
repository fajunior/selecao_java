package br.com.pitang.selecao_java.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pitang.selecao_java.entities.User;
import br.com.pitang.selecao_java.exceptions.FieldValueExistisException;
import br.com.pitang.selecao_java.exceptions.NotNullableAttributeException;
import br.com.pitang.selecao_java.services.UserService;
import br.com.pitang.selecao_java.vo.ErrorMessageVO;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = {RequestMethod.POST})
	public Object createUser(@RequestBody User user) {
		try {
			userService.createUser(user);
		} catch (FieldValueExistisException | NotNullableAttributeException e) {
			ErrorMessageVO vo = new ErrorMessageVO(e.getMessage(), e.getCodeError());
			e.printStackTrace();
			return new ResponseEntity<>(vo, HttpStatus.BAD_REQUEST);
		}
		return user;
	}
	
	@RequestMapping(method = {RequestMethod.GET})
	public Iterable<User> list() {
		return userService.list();
	}
	
	@GetMapping(path = "/{id}")
	public Optional<User> findById(@PathVariable int id) {
		return userService.findById(id);
	}
	
	@DeleteMapping(path = "/{id}")
	public void remove(@PathVariable int id) {
		userService.remove(id);
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@PathVariable int id, @RequestBody User user) {
		userService.updateUser(id, user);
		return user;
	}
	
	
}