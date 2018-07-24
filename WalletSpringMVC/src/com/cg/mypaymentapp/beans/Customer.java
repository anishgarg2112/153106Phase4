package com.cg.mypaymentapp.beans;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Customer {

	@NotEmpty
	private String name;
	@Id
	@NotEmpty
	private String mobileNo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;

	@OneToMany(fetch = FetchType.EAGER,mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Transaction> transaction = new HashSet<>();

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public Customer(String name, String mobileNo, Wallet wallet) {
		this.name = name;
		this.mobileNo = mobileNo;
		this.wallet = wallet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	@Override
	public String toString() {
		return "Customer name=" + name + ", mobileNo=" + mobileNo + wallet;
	}

	public Set<Transaction> getTransaction() {
		return transaction;
	}

	public void addTransaction(Transaction trans) {
		trans.setCustomer(this); // this will avoid nested cascade
		this.getTransaction().add(trans);
	}

	public void setTransaction(Set<Transaction> transaction) {
		this.transaction = transaction;
	}

}
