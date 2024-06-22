package br.com.pitang.selecao_java.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotNull
	private int year;
	@NotNull
	@NotBlank
	@Column(unique = true)
	private String licensePlate;
	@NotNull
	private String model;
	@NotNull
	private String color;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonManagedReference
	private User user;

	public Car() {
	}

	public Car(int year, String licensePlate, String model, String color) {
		super();
		this.year = year;
		this.licensePlate = licensePlate;
		this.model = model;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", year=" + year + ", licensePlate=" + licensePlate + ", model=" + model + ", color="
				+ color + "]";
	}

}
