package com.websystique.springmvc.controller;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.websystique.springmvc.dao.LogDAO;
import com.websystique.springmvc.model.Account;
import com.websystique.springmvc.service.AccountService;
import com.websystique.springmvc.model.Log;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.UserProfile;
import com.websystique.springmvc.model.CreditCard;
import com.websystique.springmvc.model.Transaction;
import com.websystique.springmvc.service.LogService;
import com.websystique.springmvc.service.OtpMailService;
import com.websystique.springmvc.service.UserProfileService;
import com.websystique.springmvc.service.UserService;
import com.websystique.springmvc.service.CreditCardService;
import com.websystique.springmvc.service.TransactionService;

import com.websystique.springmvc.model.Transc_mstr;
import com.websystique.springmvc.model.acc_mstr;
import com.websystique.springmvc.service.extuserservice;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {

	@Autowired
	private extuserservice extUserService;


	@Qualifier(value="extUserService")
	public void setUserService(extuserservice us){
		this.extUserService = us;
	}


	/*---------*/

	@Autowired
	UserService userService;

	@Autowired
	OtpMailService otpMailService;

	@Autowired
	AccountService accountService;

	@Autowired
	LogService logService;

	@Autowired
	LogDAO logDAO;

	@Autowired
	CreditCardService creditCardService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

	@RequestMapping(value = {"/","/home" }, method = RequestMethod.GET)
	public String displayHome(ModelMap model) {
		model.addAttribute("exp", "");
		try{

			System.out.println("INTO THE HOME CONTROLLER");
			//		List<User> users = userService.findAllUsers();
			//		model.addAttribute("users", users);
			model.addAttribute("loggedinuser", getPrincipal());
			System.out.println("just before the function");
			DateFormat dateFormat = new SimpleDateFormat("MMddyyyy hh:mm:ss");
			Date date = new Date();
			String ts = dateFormat.format(date);
			logDAO.updateLogs("User  " + getPrincipal() + " reached home page at "
					+ ts);
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "home";
	}

	/**
	 * This method will list all existing users.
	 */
	@RequestMapping(value = {"/list" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		model.addAttribute("exp", "");
		try{
			List<User> users = userService.findAllUsers();
			model.addAttribute("users", users);
			model.addAttribute("loggedinuser", getPrincipal());}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "userslist";
	}

	@RequestMapping("/downloadLogs")
	public String downloadLogs(Model model) {
		model.addAttribute("exp", "");
		model.addAttribute("downloadLogs", " ");
		try{
			logDAO.downloadLogs();
			model.addAttribute("downloadLogs", "Logs downloaded in Download folder");
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "home";
	}

	@RequestMapping("/pending_trans")
	public String PendingTransactions(Model model){
		model.addAttribute("exp", "");
		try{
			List<Transaction> t = transactionService.getPendingTransactions();
			model.addAttribute("transactions", t);
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "pending_trans";
	}
	/**
	 * This method will list all existing users.
	 */


	/**
	 * This method will provide the medium to add a new user.
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.GET)
	public String newUser(ModelMap model) {
		model.addAttribute("exp", "");
		try{
			User user = new User();
			Account acc = new Account();
			model.addAttribute("account",acc);
			model.addAttribute("user", user);
			model.addAttribute("edit", false);
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "registration";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/newuser" }, method = RequestMethod.POST)
	public String saveUser(@Valid User user, HttpServletRequest req,BindingResult result,
			ModelMap model) {

		try{
			if (result.hasErrors()) {
				return "registration";
			}
			UserProfile a[] = new UserProfile[user.getUserProfiles().size()];
			int i=0;
			for(UserProfile u: user.getUserProfiles()){

				a[i++]=u;

			}
			/*
			 * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
			 * and applying it on field [sso] of Model class [User].
			 * 
			 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
			 * framework as well while still using internationalized messages.
			 * 
			 */
			if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
				FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
				result.addError(ssoError);
				return "registration";
			}
			//boolean uniqueEmail = userService.isEmailUnique(user.getEmail());
			if(!userService.isPhoneUnique(user.getPhone())){
				FieldError ssoError = new FieldError("user", "phone", "Already in Use");
				result.addError(ssoError);
				return "registration";
			}

			if(!userService.isEmailUnique(user.getEmail())){
				FieldError ssoError = new FieldError("user", "email", "Already in Use");
				result.addError(ssoError);
				return "registration";
			}



			user.setTypeId(Character.getNumericValue((user.getUserProfiles().toString().charAt(17))));
			userService.saveUser(user);

			int ids = user.getTypeId();

			if(ids == 4 || ids == 5){
				Account defaultAccount = new Account();
				int max_acc = accountService.getMaxTransaction();
				defaultAccount.setAccount_no(max_acc+1);
				defaultAccount.setAccount_type_id(1);
				defaultAccount.setBalance(1000);
				int max_uid = userService.getMaxId();
				defaultAccount.setStatus("Active");
				defaultAccount.setUid(max_uid);
				accountService.saveAccount(defaultAccount);		


				Random rand = new Random();

				int randomNum = rand.nextInt((999-100) + 1) + 100;

				CreditCard defaultCard = new CreditCard();
				long max_card = creditCardService.getMaxCard();
				System.out.println("MAX CARD!"+max_card);
				defaultCard.setCardId(max_card+1);
				defaultCard.setAccountId(max_acc+1);
				defaultCard.setAmountSpent(0);
				defaultCard.setChName(user.getFirstName());
				defaultCard.setCreditLimit(1000);
				defaultCard.setCvv(randomNum);
				defaultCard.setExpDate("");
				defaultCard.setInterestAmount(0);
				defaultCard.setLateFee(0);
				defaultCard.setUid(user.getId());
				defaultCard.setSnn(user.getSsn());
				creditCardService.saveCard(defaultCard);
			}




			model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
			model.addAttribute("loggedinuser", getPrincipal());
		}
		catch(Exception E){
			return "registration";
		}
		return "registrationsuccess";
	}


	/**
	 * This method will provide the medium to update an existing user.
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable String ssoId, ModelMap model) {
		model.addAttribute("exp", "");
		try{
			User user = userService.findBySSO(ssoId);
			model.addAttribute("role",user.getTypeId());
			model.addAttribute("user", user);
			model.addAttribute("edit", true);
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}

		return "registration";
	}
	@RequestMapping(value = {"/approve-{transactionId}" }, method = RequestMethod.GET)
	public String ApproveTransactions(@PathVariable String transactionId, ModelMap model) {		
		try{
			List<Transaction> tx=transactionService.updateTransaction(Integer.parseInt(transactionId));
			accountService.transferFundAfterAuthorize(getPrincipal(), Integer.parseInt(tx.get(0).getTrxMerchant()), tx.get(0).getTrxAmount());

			//		List<User> users = userService.findAllUsers();
			//		model.addAttribute("users", users);
			//		model.addAttribute("loggedinuser", getPrincipal());
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "home";
	}



	/**
	 * This method will be called on form submission, handling POST request for
	 * updating user in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-user-{ssoId}" }, method = RequestMethod.POST)
	public String updateUser(@Valid User user, BindingResult result,
			ModelMap model, @PathVariable String ssoId) {
		model.addAttribute("exp", "");
		try{	
			if (result.hasErrors()) {
				return "registration";
			}

			/*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
		if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
			FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
		    result.addError(ssoError);
			return "registration";
		}*/


			userService.updateUser(user);

			model.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
			model.addAttribute("loggedinuser", getPrincipal());}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "registrationsuccess";
	}


	/**
	 * This method will delete an user by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-user-{ssoId}" }, method = RequestMethod.GET)
	public String deleteUser(Model model, @PathVariable String ssoId) {
		model.addAttribute("exp", "");
		try{
			userService.deleteUserBySSO(ssoId);}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "redirect:/list";
	}


	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}

	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "accessDenied";
	}

	/**
	 * This method handles Access-Denied redirect.
	 */
	@RequestMapping(value = "/viewAccounts", method = RequestMethod.GET)
	public String viewAccountsPage(ModelMap model) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
		}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "viewAccounts";
	}

	/**
	 * This method handles login GET requests.
	 * If users is already logged-in and tries to goto login page again, will be redirected to list page.
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		if (isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("MMddhhmmss hh:mm:ss");
			Date date = new Date();
			String ts = dateFormat.format(date);
			System.out.println("logged in");
			logDAO.updateLogs("User  " + getPrincipal() + " logged in at" + ts);
			return "redirect:/home";
		}
	}

	/**
	 * This method handles logout requests.
	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		model.addAttribute("exp", "");
		try{


			DateFormat dateFormat = new SimpleDateFormat("MMddyyyy hh:mm:ss");
			Date date = new Date();
			String ts = dateFormat.format(date);
			logDAO.updateLogs("User  " + getPrincipal() + " loggedout at " + ts);
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				// new SecurityContextLogoutHandler().logout(request, response,
				// auth);
				persistentTokenBasedRememberMeServices.logout(request, response,
						auth);
				SecurityContextHolder.getContext().setAuthentication(null);
			}}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "redirect:/login?logout";
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	/**
	 * This method returns true if users is already authenticated [logged-in], else false.
	 */
	private boolean isCurrentAuthenticationAnonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authenticationTrustResolver.isAnonymous(authentication);
	}


	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String listAccounts(Model model) {
		model.addAttribute("exp", "");
		try{


			String ssoid=getPrincipal();
			model.addAttribute("account", new Account());
			//model.addAttribute("listPersons", this.personService.listPersons());
			if(accountService.listAccounts(ssoid) == null) {
				System.out.println("NUllllll");
			}
			model.addAttribute("listAccounts", accountService.listAccounts(ssoid));
			model.addAttribute("loggedinuser", getPrincipal());
		}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "account";
	}

	@RequestMapping(value = "/creditcard", method = RequestMethod.GET)
	public String creditCardDetails(Model model) {
		model.addAttribute("exp", "");
		try{
			String user = getPrincipal();
			model.addAttribute("card", new CreditCard());
			if(creditCardService.listCards(user) == null) {
				System.out.println("NUllllll");
			}
			model.addAttribute("listCards", creditCardService.listCards(user));
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "creditcard";
	}

	@RequestMapping("/debitCC/{amt}")
	public String debit(Model model, CreditCard card, @PathVariable("amt") double amt)
	{
		model.addAttribute("exp", "");
		try{
			this.creditCardService.debit(card,amt);
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "into debit function";
	}

	@RequestMapping("/creditCC/{amt}")
	public String credit(Model model, CreditCard card, @PathVariable("amt") double amt)
	{
		model.addAttribute("exp", "");
		try{
			this.creditCardService.credit(card,amt);}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "";
	}

	@RequestMapping(value = "validateOtpCC", method = RequestMethod.POST)
	public String validateAndPay(Model model, HttpServletRequest req,
			RedirectAttributes attr) {
		try{
			String user = getPrincipal();
			model.addAttribute("listCards", creditCardService.listCards(user));
			boolean flag=this.otpMailService.checkOtp(req.getParameter("otp"), getPrincipal());
			if(flag==true) model.addAttribute("otpstatus", "Correct OTP, transaction sent to Bank for approval ");
			else model.addAttribute("otpstatus", " Wrong OTP. Transaction unsuccessful ");
		}catch(Exception e){
			model.addAttribute("otpstatus", "Something went wrong ");
		}
		return "creditcard";
	}

	@RequestMapping(value = "makePayment", method = RequestMethod.POST)
	public String makePayment(Model model, HttpServletRequest req,
			RedirectAttributes attr) {
		model.addAttribute("generror","");
		model.addAttribute("wronginput","");
		model.addAttribute("creditcard", new CreditCard());
		model.addAttribute("status","");
		model.addAttribute("loggedinuser", getPrincipal());
		String stats = null;
		try{
			String user = getPrincipal();
			model.addAttribute("listCards", creditCardService.listCards(user));
			if(Double.parseDouble(req.getParameter("amount"))<0 || 
					req.getParameter("amount").isEmpty() ||
					Integer.parseInt(req.getParameter("toAcc"))<0||
					req.getParameter("toAcc").isEmpty()
					){
				model.addAttribute("wronginput","Input is in wrong format");
			}else{
				if (creditCardService.listCards(user) == null) {
					System.out.println("NUllllll");
				}
				model.addAttribute("listCards", creditCardService.listCards(user));
				stats=this.creditCardService.makePayment(user,
						Integer.parseInt(req.getParameter("toAcc")),
						Double.parseDouble(req.getParameter("amount")));
			}
		}catch(Exception e){
			model.addAttribute("generror","Something went wrong");
		}
		model.addAttribute("status",stats);
		return "creditcard";
	}

	@RequestMapping(value ="cctransaction", method = RequestMethod.POST)
	public String ccTransactions(Model model, HttpServletRequest req, RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
			String user = getPrincipal();
			model.addAttribute("card", new CreditCard());
			if(creditCardService.listCards(user) == null) {
				System.out.println("NUllllll");
			}
			model.addAttribute("listCards", creditCardService.listCards(user));
			if(req.getParameter("display")!=null){
				model.addAttribute("listcctransactions",this.creditCardService.getTransaction(user));
			}}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "creditcard";
	}

	@RequestMapping("/interestGenerationCC/{amt}")
	public String interestGeneration(CreditCard card)
	{
		this.creditCardService.interestGeneration(card);
		return "";
	}

	@RequestMapping("/latePaymentCC/{amt}")
	public String latePaymentFee(CreditCard card)
	{
		this.creditCardService.latePaymentFee(card);
		return "";
	}

	@RequestMapping(value ="creditlimit", method = RequestMethod.POST)
	public String newCreditLimit(Model model, HttpServletRequest req, RedirectAttributes attr)
	{
		model.addAttribute("exp", "");
		try{
			model.addAttribute("creditcard", new CreditCard());
			model.addAttribute("loggedinuser", getPrincipal());
			String user = getPrincipal();
			if(creditCardService.listCards(user) == null) {
				System.out.println("NUllllll");
			}
			model.addAttribute("listCards", creditCardService.listCards(user));
			model.addAttribute("newcreditlimit", this.creditCardService.newCreditLimit(user));
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "creditcard";
	}	

	@RequestMapping("/edit/{id}")
	public String editAccount(@PathVariable("id") int id, Model model){
		model.addAttribute("exp", "");
		try{
			model.addAttribute("account", this.accountService.getAccountById(id));
			model.addAttribute("listAccounts", this.accountService.listAccounts(getPrincipal()));
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "account";
	}


	@RequestMapping(value= "/account/add", method = RequestMethod.POST)
	public String addPerson(Model model, @ModelAttribute("account") Account c){
		model.addAttribute("exp", "");
		try{
			if(c.getAccount_no() == 0){
				//new person, add it
				this.accountService.addAccount(c);
			}else{
				//existing person, call update
				this.accountService.updateAccount(c);
			}
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "redirect:/accounts";

	}


	@RequestMapping("/debit/{amt}")
	public String debit(Model model, Account acc, @PathVariable("amt") double amt)

	//public String debit(Account acc, @PathVariable("amt") double amt,Model model)
	{
		model.addAttribute("exp", "");
		try{
			this.accountService.debit(acc,amt);
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "into debit function";

		//model.addAttribute("debit", this.accountService.debit(acc,amt));
	}

	@RequestMapping("/credit/{amt}")
	public String credit(Model model, Account acc, @PathVariable("amt") double amt)
	{
		model.addAttribute("exp", "");
		try{
			this.accountService.credit(acc,amt);
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "";
	}

	@RequestMapping(value ="payCreditCard", method = RequestMethod.POST)
	public String payCreditCard(Model model, HttpServletRequest req, RedirectAttributes attrt)
	{
		model.addAttribute("exp", "");
		try{
			model.addAttribute("account", new Account());
			model.addAttribute("loggedinuser", getPrincipal());
			String user = getPrincipal();
			if(creditCardService.listCards(user) == null) {
				System.out.println("NUllllll");
			}
			model.addAttribute("listCards", creditCardService.listCards(user));
			model.addAttribute("payCCBill",this.accountService.payCreditCard(user, Double.parseDouble(req.getParameter("amount"))));
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "creditcard";
	}

	@RequestMapping(value = "/transactions", method = RequestMethod.GET)
	public String transactionDetails(Model model) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("transactions", new Transaction());
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "transactions";
	}

	@RequestMapping(value = "/transactions/latestTransactions", method = RequestMethod.GET)
	public String listTransactions(Model model, int accountNum) {
		model.addAttribute("exp", "");
		try{
			this.transactionService.getLatestTrnsactions(accountNum);
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "";
	}

	@RequestMapping(value = "/viewtransactions", method = RequestMethod.GET)
	public String viewTransactionsPage(Model model) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "viewtransactions";
	}

	@RequestMapping("/downloadPDF")
	public String downloadPDF(Model model){
		model.addAttribute("exp", "");
		model.addAttribute("dstats", "");
		try{
			model.addAttribute("account", new Account());
			//model.addAttribute("users", users);
			String user = getPrincipal();
			model.addAttribute("loggedinuser", getPrincipal());
			this.transactionService.getTransactionsPDF(user);
			model.addAttribute("dstats", "Successfully downlaoded in Downloads folder");
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "viewtransactions";

	}


	@RequestMapping(value = "/managefunds", method = RequestMethod.GET)
	public String viewManageFundsPage(Model model) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "managefunds";
	}

	@RequestMapping(value ="validateOtp", method = RequestMethod.POST)
	public String checkOtp(Model model, HttpServletRequest req, RedirectAttributes attr) {
		model.addAttribute("exp", "");
		model.addAttribute("otpstatus", "");
		try{
			boolean flag=this.otpMailService.checkOtp(req.getParameter("otp"), getPrincipal());
			if(flag==true) model.addAttribute("otpstatus", "Correct OTP, transaction sent to Bank for approval ");
			else model.addAttribute("otpstatus", " Wrong OTP. Transaction unsuccessful ");
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "managefunds";
	}

	@RequestMapping(value = "forgotPassword", method = RequestMethod.POST)
	public String updatePassword(Model model, HttpServletRequest req,
			RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			System.out.println("+++++++++++++++++++++++++in controller");
			char[] arr = "abcdefghijklmnopqrstuvwxyx".toCharArray();
			char[] intArr = "0123456789".toCharArray();
			int k = 0;
			StringBuilder newPass = new StringBuilder();
			for (int i = 0; i < 8; i++) {
				Random rand = new Random();
				int ind = 0;
				if (k < 5) {
					ind = rand.nextInt(25);
					newPass.append(arr[ind]);
				} else {
					ind = rand.nextInt(9);
					newPass.append(intArr[ind]);
				}

				newPass.toString();
				k++;
			}
			System.out.println(newPass.toString());
			boolean flag = userService.updatePassword(newPass.toString(),
					req.getParameter("email"));
			if (flag == false || req.getParameter("email").isEmpty()) {
				model.addAttribute("emailerror", "Email not found");
			} else
				model.addAttribute("emailerror", "Password changed successfully");
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "login";
	}

	@RequestMapping(value = "transferFund", method = RequestMethod.POST)
	public String transferFunds(Model model, HttpServletRequest req,
			RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("emptyparams", "");
			model.addAttribute("txnsuccess", "");
			model.addAttribute("negbal", "");
			model.addAttribute("inputformat", "");
			model.addAttribute("account", new Account());
			model.addAttribute("loggedinuser", getPrincipal());
			String user = getPrincipal();
			if (req.getParameter("toEmail").isEmpty()
					&& req.getParameter("toPhone").isEmpty()
					&& req.getParameter("toAcc").isEmpty()) {
				model.addAttribute("emptyparams",
						"Error:All transaction types are empty");
			} else {
				try {
					if (Double.parseDouble(req.getParameter("amount")) < 0) {
						model.addAttribute("negbal", "Balance cant be negative");
					} else {
						boolean emailFlag = false;
						if (!req.getParameter("toEmail").isEmpty()) {
							emailFlag = this.accountService.transferFundUsingEmail(
									user, req.getParameter("toEmail"),
									Double.parseDouble(req.getParameter("amount")));
							if (emailFlag == true) {
								if (Double.parseDouble(req.getParameter("amount")) > 10000)
									model.addAttribute("txnsuccess",
											"Critical Transaction : OTP sent to email ");
								else
									model.addAttribute("txnsuccess",
											"transfer success");
							} else {
								model.addAttribute("txnsuccess",
										"something went wrong with transaction");
							}
						}
						boolean phoneFlag = false;
						if (!req.getParameter("toPhone").isEmpty()) {
							phoneFlag = this.accountService.transferFundUsingPhone(
									user,
									Long.parseLong(req.getParameter("toPhone")),
									Double.parseDouble(req.getParameter("amount")));
							if (phoneFlag == true) {
								if (Double.parseDouble(req.getParameter("amount")) > 10000)
									model.addAttribute("txnsuccess","Critical Transaction : OTP sent to email ");
								else
									model.addAttribute("txnsuccess",
											"transfer success");
							} else {
								model.addAttribute("txnsuccess",
										"something went wrong with transaction");
							}
						}
						boolean flag = false;
						if (!req.getParameter("toAcc").isEmpty()) {
							flag = this.accountService.transferFund(user,
									Integer.parseInt(req.getParameter("toAcc")),
									Double.parseDouble(req.getParameter("amount")));
							if (flag == true) {
								if (Double.parseDouble(req.getParameter("amount")) > 10000)
									model.addAttribute("txnsuccess","Critical Transaction : OTP sent to email ");
								else
									model.addAttribute("txnsuccess",
											"transfer success");
							} else {
								model.addAttribute("txnsuccess",
										"something went wrong with transaction");
							}
						}
					}
				} catch (Exception e) {
					model.addAttribute("inputformat", "input not in proper format");
				} finally {

				}
			}}
		catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "managefunds";
	}


	/*------------------------------------------------------------EXT USER--------------------------------------------------*/
	@RequestMapping(value ="transaction", method = RequestMethod.POST)
	public String creditAndDebit(Model model, HttpServletRequest req, RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
			String user = getPrincipal();
			int extUserId = this.extUserService.getUserId(user);
			int accNo =  Integer.parseInt(this.extUserService.getuseraccountnumbers(extUserId).get(0));
			if(req.getParameter("display")!=null){
				model.addAttribute("listtransactions",this.extUserService.getTransaction(accNo));
				return "viewtransactions";
			}
			if(req.getParameter("debitandcredit")!=null){
				Integer amount= Integer.parseInt(req.getParameter("amount")) ;
				if(amount<=0){ return "managefunds";}
				String status = this.extUserService.Updatetransactions(accNo, amount, req.getParameter("debitandcredit")) ;
				if(!status.equals("insufficient balance"))
					this.extUserService.UpdateBalance(accNo, amount,  req.getParameter("debitandcredit")) ;
				model.addAttribute("status",status);
				return "managefunds";
			}
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "account";
	}

	@RequestMapping(value = "/pendingapprovalsbyuser")
	public String pendingapprovalsbyuser(Model model,HttpServletRequest req, RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "pendingapprovalsbyuser";
	}

	@RequestMapping(value ="/approve-{transc_id}")
	public String approvemerchantrequest(@PathVariable int transc_id , Model model){
		model.addAttribute("exp", "");
		try{
			model.addAttribute("approved",this.extUserService.userapproval(transc_id)) ;
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "pendingapprovalsbyuser" ;
	}

	@RequestMapping(value ="approvals")
	public String getpendingapprovals(Model model,HttpServletRequest req, RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
			String user = getPrincipal();
			int extUserId = this.extUserService.getUserId(user);
			int accNo =  Integer.parseInt(this.extUserService.getuseraccountnumbers(extUserId).get(0));
			model.addAttribute("usermerchantrequest",this.extUserService.userauthorization(accNo)) ;
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "pendingapprovalsbyuser";
	}

	@RequestMapping(value = "/submitpaymentbymerchant")
	public String submitpaymentbymerchant(Model model,HttpServletRequest req, RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "submitpaymentbymerchant";
	}

	@RequestMapping(value ="submitpayment")
	public String submitpayment(Model model,HttpServletRequest req, RedirectAttributes attr) {
		model.addAttribute("exp", "");
		try{
			model.addAttribute("loggedinuser", getPrincipal());
			String user = getPrincipal();
			Integer amount= Integer.parseInt(req.getParameter("amount")) ;
			Integer accountno = Integer.parseInt(req.getParameter("accountno")) ;
			model.addAttribute("submitpayment",this.extUserService.merchantpaymentforuser(user, accountno, amount)) ;
			//String balstatus = this.extUserService.UpdateBalance(accountno, amount,  "debit") ;
		}catch(Exception e){
			model.addAttribute("exp", "Something went wrong");
		}
		return "submitpaymentbymerchant";
	}
}