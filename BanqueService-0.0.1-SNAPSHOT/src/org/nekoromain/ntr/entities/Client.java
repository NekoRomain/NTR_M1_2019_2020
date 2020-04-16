package org.nekoromain.ntr.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Client implements Serializable{
	
	private static final long serialVersionUID = -1151877053969001031L;
	private Integer id;
	private String name;
	private Double solde;
	
	public Client() {
		this.id = 0;
		this.name = "Default";
		this.solde=0.;
	}
	
	public Client(int id, String name, double solde) {
		this.id = id;
		this.name = name;
		this.solde = solde;
		}

	
	/// GETTERS AND SETTERS ///
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSolde() {
		return solde;
	}

	public void setSolde(double solde) {
		this.solde = solde;
	}

	
}
