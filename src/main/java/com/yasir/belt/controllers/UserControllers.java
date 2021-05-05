package com.yasir.belt.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.yasir.belt.models.LoginUser;
import com.yasir.belt.models.Rating;
import com.yasir.belt.models.Show;
import com.yasir.belt.models.User;
import com.yasir.belt.services.UserService;

@Controller
public class UserControllers {
	
	private static UserService userServ;
	
	public UserControllers(UserService userServ) {
		this.userServ = userServ;
	}
	
	@GetMapping("/sign_in")
	public String signIn(Model model) {
		model.addAttribute("registeringUser", new User());
		model.addAttribute("loginUser", new LoginUser()); //made a login user class, so it should automatically connect
		return "signIn.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("registeringUser") User registeringUser, BindingResult result, Model model, HttpSession session) {
		if(!registeringUser.getPassword().equals(registeringUser.getConfirm())) {
			result.rejectValue("confirm", "Match", "Confirm Password must match Password");
			System.out.println(registeringUser);
		}
		if(userServ.getUser(registeringUser.getEmail()) != null) {
			result.rejectValue("email", "Unique", "Email already in use");
			System.out.println(registeringUser);
		}
		
		if(result.hasErrors()) {
			model.addAttribute("loginUser", new LoginUser()); //made a login user class, so it should automatically connect
			return "signIn.jsp";
		} else {
			session.setAttribute("user", userServ.create(registeringUser));
			System.out.println(registeringUser);
			return"redirect:/shows";
		}
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginUser") LoginUser loginUser, BindingResult result, Model model, HttpSession session) {
		User loggingInUser =userServ.login(loginUser, result);
		if(result.hasErrors()) {
			model.addAttribute("registeringUser", new User());
			return "signIn.jsp";
		} else {
			//TODO:deal with session
			session.setAttribute("user", loggingInUser);
			return "redirect:/shows";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/sign_in";
	}
//__________________________________________________________________________________________________________________________________________________
	@GetMapping("/shows")
	public String displayHome(Model model, HttpSession session) {  //including httpSession in here to make sure youre logged in to be in the home page
		User loggedInUser = (User) session.getAttribute("user"); //this is getting user from session
		if(loggedInUser == null) { //this part means that no one is logged in, then I will logout
			return "redirect:/logout";
		}
		model.addAttribute("allShows", userServ.getShows()); //here we will use Model model to display whats in our db for all shows and networks on this page specifically
		return "home.jsp";
	}
	
	@GetMapping("/shows/new")
	public String addShow(Model model) { //adding model here to get the model attribute form going
		model.addAttribute("newShow", new Show());
		return "addShow.jsp";
	}
	
	@PostMapping("/create/show")
	public String creatingShow(@Valid @ModelAttribute("newShow") Show newShow, BindingResult result, HttpSession session) { 
		if(result.hasErrors()) {
			return "addShow.jsp";
		}else {
			userServ.createShow(newShow);
			return "redirect:/shows"; //valid used to validate 
		}	
	}
	
	@GetMapping("/shows/{id}")
	public String displaySingleShow(@PathVariable("id") Long id, Model model, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user"); //this is getting user from session
		if(loggedInUser == null) { //this part means that no one is logged in, then I will logout
			return "redirect:/logout";
		}
		model.addAttribute("singleShow", userServ.getOne(id)); //here we are using singleShow in the jsp to display, we brought this down here
		model.addAttribute("newRating", new Rating()); //needs to be empty
		return "ratePage.jsp";
	}
	
	@PostMapping("/shows/{id}/rating")
	public String addRating(@Valid @ModelAttribute("newRating") Rating newRating, BindingResult result, @PathVariable("id") Long id, Model model, HttpSession session) {
		User loggedInUser = (User) session.getAttribute("user"); //this is getting user from session
		if(result.hasErrors()) {
			model.addAttribute("singleShow", userServ.getOne(id)); //here we are using singleShow in the jsp to display, we brought this down here
			return "rating.jsp";
		} 
		newRating.setShow(userServ.getOne(id)); //ISSUE HERE
		newRating.setUser(loggedInUser);
		Rating r = userServ.createRating(newRating);
		if(r == null) {
			result.rejectValue("rating", "unique", "you have done a review already");
			model.addAttribute("singleShow", userServ.getOne(id));  //HERE WE ARE DOING CHECKS
			return "ratePage.jsp";
		}
		return "redirect:/shows/" + id;
	}
	
	
	@GetMapping("/shows/{id}/edit")
	public String editShow(@PathVariable("id") Long id, Model model) {
		model.addAttribute("singleShow", userServ.getOne(id));
		return "edit.jsp";
	}
	
	@PostMapping("/edit/update/{id}")
	public String editSingleShow(@PathVariable("id") Long id, @Valid @ModelAttribute("singleShow") Show singleShow,
			BindingResult result) {
		if(result.hasErrors()) {
			return "edit.jsp";
		} else {
			userServ.update(singleShow, id);
			return "redirect:/shows";
		}	
	}
	
	@GetMapping("/show/{id}/delete")
	public String deleteSingleShow(@PathVariable("id") Long id, @Valid @ModelAttribute("singleShow") Show singleShow,
			BindingResult result) {
		userServ.destroy(singleShow, id);
		return "redirect:/shows";
	}
}
