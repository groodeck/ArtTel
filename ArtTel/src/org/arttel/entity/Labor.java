package org.arttel.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Labor")
public class Labor {

	@Id
	@Column(name = "LaborId")
	private Integer laborId;
	
	@Column(name = "NumerZlecenia")
	private String orderNumber;
	
	@Column(name = "Miasto")
	private String city;
	
	@Column(name = "Adres")
	private String address;
	
	@Column(name = "NazwaPionu")
	private String sectionName;
	
	@Column(name = "Rodzaj")
	private String laborType;
	
	@Column(name = "Ilosc")
	private Integer quantity;
	
	@Column(name = "JednostkaMiary")
	private String measurmentUnit;
	
	@Column(name = "UserId")
	private String userId;
	
}
