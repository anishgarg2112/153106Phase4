package com.cg.mypaymentapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transaction;
import com.cg.mypaymentapp.service.WalletService;

@Controller
public class CustomerActionController {

	@Autowired(required = true)
	private WalletService walletService;

	@RequestMapping(value = "/registerCustomer", method = RequestMethod.POST)
	public ModelAndView registerCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult result) {
		if (result.hasErrors())
			return new ModelAndView("registration");
		try {
			customer = walletService.createAccount(customer);
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("registration", "errormessage", e.getMessage());
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("showBalance", "customer", customer);
		return modelAndView;

	}

	@RequestMapping(value = "/loginSuccess", method = RequestMethod.POST)
	public ModelAndView loginSuccess(@ModelAttribute("customer") Customer customer) {

		try {
			customer = walletService.showBalance(customer.getMobileNo());
		} catch (Exception e) {

			ModelAndView modelAndView = new ModelAndView("login", "errormessage", e.getMessage());
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("loginSuccess");
		return modelAndView;

	}

	@RequestMapping(value = "/showBalance", method = RequestMethod.POST)
	public ModelAndView showBalance(@ModelAttribute("customer") Customer customer) {

		try {
			customer = walletService.showBalance(customer.getMobileNo());

		} catch (Exception e) {

			ModelAndView modelAndView = new ModelAndView("checkBalance", "errormessage", e.getMessage());
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("showBalance", "customer", customer);
		return modelAndView;

	}

	@RequestMapping(value = "/withdrawAmount", method = RequestMethod.POST)
	public ModelAndView withdrawAmount(@ModelAttribute("customer") Customer customer) {

		try {
			customer = walletService.withdrawAmount(customer.getMobileNo(), customer.getWallet().getBalance());
		} catch (Exception e) {

			ModelAndView modelAndView = new ModelAndView("withdrawAmount", "errormessage", e.getMessage());
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("showBalance", "customer", customer);
		return modelAndView;

	}

	@RequestMapping(value = "/depositAmount", method = RequestMethod.POST)
	public ModelAndView depositAmount(@ModelAttribute("customer") Customer customer) {

		try {
			customer = walletService.depositAmount(customer.getMobileNo(), customer.getWallet().getBalance());
		} catch (Exception e) {

			e.printStackTrace();
			ModelAndView modelAndView = new ModelAndView("depositAmount", "errormessage", e.getMessage());
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("showBalance", "customer", customer);
		return modelAndView;

	}

	@RequestMapping(value = "/fundTransfer", method = RequestMethod.POST)
	public ModelAndView fundTransfer(@ModelAttribute("customer") Customer customer, HttpServletRequest req) {

		try {
			customer = walletService.fundTransfer(customer.getMobileNo(), req.getParameter("mobile2"),
					customer.getWallet().getBalance());
		} catch (Exception e) {

			ModelAndView modelAndView = new ModelAndView("fundTransfer", "errormessage", e.getMessage());
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("showBalance", "customer", customer);
		return modelAndView;

	}

	@RequestMapping(value = "/showTransaction", method = RequestMethod.POST)
	public ModelAndView showTransaction(@ModelAttribute("customer") Customer customer) {
		List<Transaction> trans =null;
		try {

			trans = walletService.showTransactions(customer.getMobileNo());
			System.out.println(trans);
		} catch (Exception e) {

			ModelAndView modelAndView = new ModelAndView("showTransactions", "errormessage", e.getMessage());
			return modelAndView;
		}

		ModelAndView modelAndView = new ModelAndView("getTransactionDetails");
		modelAndView.addObject("transaction",trans);
		return modelAndView;

	}

}
