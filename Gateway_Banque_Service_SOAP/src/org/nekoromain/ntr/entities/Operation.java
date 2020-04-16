package org.nekoromain.ntr.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement
public class Operation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4287232279233950777L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
    @Column(name = "ido",unique=true, nullable = false)
	private Integer id;
	private Integer idClient;
	private String date;
	private int type;
	private Double amount;
	//formatteur pour la date
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	
	public Operation() {
		idClient = -1;
		type = -1;
		amount = -1.;
		date = null;
	}
	
	public Operation(int id,int idc, String date, int type, double amount) {
		this.id = id;
		this.idClient= idc;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}
	
	public Operation(int idc, String date, int type, double amount) {
		this.idClient= idc;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}
	
	public Operation(int idc, int type, double amount) {
		this.idClient= idc;
		this.date = formatter.format(new Date());
		this.type = type;
		this.amount = amount;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	@Override
	public String toString() {
		String typeOp = (type==0) ? "DEBIT" : "CREDIT";
		
		return "Type: " + typeOp + "\n"
				+ "Montant: " + amount + "\n"
				+"Date: " + date;
		
	}
	
}
