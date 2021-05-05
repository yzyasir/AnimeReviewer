package com.yasir.belt.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.yasir.belt.models.LoginUser;
import com.yasir.belt.models.Rating;
import com.yasir.belt.models.Show;
import com.yasir.belt.models.User;
import com.yasir.belt.repositories.RatingRepository;
import com.yasir.belt.repositories.ShowRepository;
import com.yasir.belt.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepo;
	private static RatingRepository ratingRepo;
	private static ShowRepository showRepo;
	
	public UserService(UserRepository userRepo, RatingRepository ratingRepo, ShowRepository showRepo) { //can only have one constructor if i remember properly
		this.userRepo = userRepo;
		this.ratingRepo = ratingRepo;
		this.showRepo = showRepo;
	}
	
	public User create(User registeringUser) {
		String hashed = BCrypt.hashpw(registeringUser.getPassword(), BCrypt.gensalt());
		registeringUser.setPassword(hashed);
		return userRepo.save(registeringUser);
	}
	
	public User getUser(String email) {
		Optional<User> potentialUser = userRepo.findByEmail(email); //this method checks if we already have the email
		return potentialUser.isPresent() ? potentialUser.get() : null;
	}
	
	public User login(LoginUser loginUser, BindingResult result) {
		if(result.hasErrors()) {
			return null; //checking if user even exists in this method
		}
		User existingUser = getUser(loginUser.getEmail());
		if(existingUser == null) {
			result.rejectValue("email", "unique", "Email already in use");
			return null;
		}
		if(BCrypt.checkpw(loginUser.getPassword(), existingUser.getPassword())) {
			result.rejectValue("password", ",atches", "incorrect password");
			return null;
		}
		return existingUser;
	}
	
//_____________________________________________________________________________________________

	public Show createShow(Show newShow) {
		return showRepo.save(newShow); //newSHow can then be used in controllers to create
	}
	
	public Rating createRating(Rating newRating) {
		List<Rating> matchingRatings = ratingRepo.matchingRatings(newRating.getUser().getId(), newRating.getShow().getId());
		if(matchingRatings.size() > 0) {
			return null; //herrrrrererer
		}
		newRating.setId(null);
	return ratingRepo.save(newRating); //newRating can then be used in controllers to create
	}
	
	public List<Show> getShows() { //we use GetShows to display
		return (List<Show>) showRepo.findAll();
	}
	
	public Show getOne(Long id) {
		Optional<Show> show = showRepo.findById(id);
		return show.isPresent() ? show.get() : null;
	} 
	
	public List<Rating> getRatings() {
		return (List<Rating>) ratingRepo.findAll();
	}
	
	public Show update(Show pleaseUpdate, Long id) {
		return showRepo.save(pleaseUpdate);
	}
	
	public void destroy(Show pleaseDelete, Long id) {
		showRepo.delete(pleaseDelete);
	}
}
