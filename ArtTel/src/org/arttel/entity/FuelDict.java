package org.arttel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Dealing")
public class FuelDict {

	@Id
	@GeneratedValue
	@Column(name = "FuelDictId")
	private Integer fuelDictId;
	
	@Column(name = "Fuel")
	private String fuel;

	public Integer getFuelDictId() {
		return fuelDictId;
	}

	public void setFuelDictId(Integer fuelDictId) {
		this.fuelDictId = fuelDictId;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	
}
