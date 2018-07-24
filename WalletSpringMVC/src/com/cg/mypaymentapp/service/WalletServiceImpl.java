package com.cg.mypaymentapp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transaction;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.WalletRepo;

@Component(value = "walletsrv")
public class WalletServiceImpl implements WalletService {

	@Autowired
	private WalletRepo repo;

	static int payee = 0;
	static String des = null;
	static String source = null;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Customer createAccount(Customer customer) {

		return repo.save(customer);
	}

	@Override
	public Customer showBalance(String mobileNo) {
		Customer customer = repo.findOne(mobileNo);


		if (customer != null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) {
		des = targetMobileNo;
		source = sourceMobileNo;
		if (sourceMobileNo.equals(targetMobileNo))
			throw new InvalidInputException("Source and target mobile number cannot be same");

		if (sourceMobileNo == null || targetMobileNo == null || amount == null)
			throw new NullPointerException();

		payee = 1;
		Customer sourceCustomer = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		payee = 0;

		return sourceCustomer;
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) {

		Customer customer = repo.findOne(mobileNo);

		if (customer == null)
			throw new InvalidInputException("Mobile number does not exist: " + mobileNo);

		Wallet wallet = customer.getWallet();
		BigDecimal oldamount = wallet.getBalance();
		wallet.setBalance(oldamount.add(amount));
		customer.setWallet(wallet);
		Transaction trans = new Transaction();
		trans.setTrans_type("Credit");
		trans.setAmount(amount);
		if (payee == 0)
			trans.setPay_to("self deposit");
		else
			trans.setPay_to(source);

		customer.addTransaction(trans);

		try {
			repo.save(customer);
		} catch (Exception e) {
			throw new InvalidInputException("Mobile number does not Exist:" + mobileNo);
		}
		return customer;

	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) {
		Customer customer = repo.findOne(mobileNo);
		if (customer == null)
			throw new InvalidInputException("Mobile number does not exist: " + mobileNo);

		Wallet wallet = customer.getWallet();
		BigDecimal oldamount = wallet.getBalance();
		int res = amount.compareTo(oldamount);
		if (res == 1) {
			throw new InsufficientBalanceException("Cannot withdraw Insufficient Balance");
		}

		wallet.setBalance(oldamount.subtract(amount));
		customer.setWallet(wallet);
		Transaction trans = new Transaction();
		trans.setTrans_type("debit");
		trans.setAmount(amount);
		if (payee == 0)
			trans.setPay_to("self withdraw");
		else
			trans.setPay_to(des);

		customer.addTransaction(trans);

		try {
			repo.save(customer);
		} catch (Exception e) {
			throw new InvalidInputException("Mobile number does not Exist:" + mobileNo);
		}

		return customer;
	}

	public boolean isValid(Customer customer) {
		String regexName = "[a-zA-Z]*";
		String regexNum = "[1-9][0-9]{9}";
		if (customer.getName().equals(null) || customer.getMobileNo().equals(null)
				|| customer.getWallet().getBalance().equals(null))
			throw new NullPointerException();
		if (customer.getName().matches(regexName) && customer.getMobileNo().matches(regexNum))
			return true;
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<Transaction> showTransactions(String mobileNo) {
		Customer customer = repo.findOne(mobileNo);

		if (customer == null)
			throw new InvalidInputException("Mobile number does not exist: " + mobileNo);
		Customer cutomer = repo.findOne(mobileNo);

		List<Transaction> trans = new ArrayList<Transaction>();
		trans.addAll(cutomer.getTransaction());

		return trans;
	}
}
