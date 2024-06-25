package br.com.pitang.selecao_java.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@Column(nullable = false, unique = true)
	private String email;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	@Column(nullable = false, unique = true)
	private String login;
	@NotNull
	private String password;
	private String phone;
	
	@ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Car> cars;

	public User() {
		super();
		 this.cars = new ArrayList<>();
	}

	public User(@NotNull String firstName, @NotNull String lastName, @NotNull String email,
			@NotNull LocalDateTime birthday, @NotNull String login, @NotNull String password, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.login = login;
		this.password = password;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	
	

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
	public void addCar(Car car) {
        cars.add(car);
        car.setUser(this); // Set the owner of the relationship
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.setUser(null); // Remove the owner of the relationship
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", birthday=" + birthday + ", login=" + login + ", password=" + password + ", phone=" + phone + "]";
	}
}