package com.cg.mypaymentapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cg.mypaymentapp.beans.Customer;

@Controller
public class URIController {
	@RequestMapping(value="/")
	public String getIndexPage()
	{
		return "index";
	}
	@RequestMapping(value="/Registration")
	public String getRegistrationPage()
	{
		return "registration";
	}
	
	@RequestMapping(value="/transactions")
	public String getTransactionPage()
	{
		return "showTransactions";
	}
	@RequestMapping(value="/login")
	public String getLoginPage()
	{
		return "login";
	}
	@RequestMapping(value="/deposit")
	public String getdeposit()
	{
		return "depositAmount";
	}
	@RequestMapping(value="/withdraw")
	public String getWithdraw()
	{
		return "withdrawAmount";
	}
	@RequestMapping(value="/fundTransfer")
	public String getfund()
	{
		return "fundTransfer";
	}
	@RequestMapping(value="/checkBalance")
	public String getBalance()
	{
		return "checkBalance";
	}
	
	@ModelAttribute("customer")
	public Customer getCustomer(){
		return new Customer();
		
	}
}
