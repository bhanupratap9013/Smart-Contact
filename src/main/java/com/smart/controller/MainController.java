package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.SmartContactManagerDao;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {
	@Autowired
	private SmartContactManagerDao smartContactManagerDao;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/signin")
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String getHome(Model model) {
		model.addAttribute("title","Home Page");
		return "home";
	} 
	
	@GetMapping("/signup")
	public String getSignUp(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	} 
	
	@PostMapping("/submit")
	public String submitForm(@Valid @ModelAttribute("user") User user,BindingResult bindingResult,@RequestParam(value = "agreement", defaultValue="false") boolean agreement ,Model model) {
		
		try {
			
			if(!agreement) {
				throw new Exception("Please check the box, agree on the terms and conditions!");
			}
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("user", user);
				return "signup";
			}
			
			user.setEnabled(true);
			user.setRole("USER");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			
			User user1 = smartContactManagerDao.save(user);
			model.addAttribute("user",new User());
			
			model.addAttribute("message",new Message("Successfully registered! ", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			model.addAttribute("message",new Message("Something went wrong! "+e.getMessage(), "alert-danger"));
			return "signup";
		}
	}
}





