package com.launchdarkly.crud.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.launchdarkly.crud.entities.User;
import com.launchdarkly.crud.repositories.UserRepository;
import com.launchdarkly.crud.services.LaunchDarklyService;
import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.LDValue;
import com.launchdarkly.sdk.server.LDClient;

@Controller
public class UserController {

	private final UserRepository userRepository;

	private String editFeature;
	private String delFeature;

	@Autowired
	private LaunchDarklyService launchDarklyService;
	
	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping("/signup")
	public String showSignUpForm(User user) {
		return "add-user";
	}

	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			return "add-user";
		}

		userRepository.save(user);

		checkLaunchDarklyFlags();

		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("edt", editFeature);
		model.addAttribute("del", delFeature);
		return "index";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("user", user);
		return "update-user";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) throws IOException {
		if (result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}

		userRepository.save(user);
		
		checkLaunchDarklyFlags();
		
		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("edt", editFeature);
		model.addAttribute("del", delFeature);

		return "index";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) throws IOException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);

		checkLaunchDarklyFlags();

		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("edt", editFeature);
		model.addAttribute("del", delFeature);
	
		return "index";
	}

	public String getEditFeature() {
		return editFeature;
	}

	public void setEditFeature(String editFeature) {
		this.editFeature = editFeature;
	}

	public String getDelFeature() {
		return delFeature;
	}

	public void setDelFeature(String delFeature) {
		this.delFeature = delFeature;
	}

	private void checkLaunchDarklyFlags() throws IOException {
		launchDarklyService.setFEATURE_FLAG_KEY("new-edittab");
		if (launchDarklyService.FlagStatus())
			setEditFeature("Active");
		else
			setEditFeature("InActive");
		
		
		launchDarklyService.setFEATURE_FLAG_KEY("delete-feature");
        if (launchDarklyService.FlagStatus())
			setDelFeature("Active");
		else
			setDelFeature("InActive");

	}
}
