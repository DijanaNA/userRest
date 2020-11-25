package main.java.user.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.user.entity.SignInRequest;
import main.java.user.entity.User;
import main.java.user.entity.UserProfile;
import main.java.user.services.UserService;

@Path("/user")
public class UserController {

	/*
	 * User Rest controller:
	 * 
	 * 1. Create user 2. Update user can delete user 4. Sign in user 5. Sign out
	 * user
	 */
//TODO
	//1. Create user profile
	//2. Update user profile
	
	
	
	
	@POST
	@Path("/signUp")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(User user) {
		UserService.createUser(user);
		return "User " + user.getUsername() + " is created successfully";
	}

	@PUT
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateUser(@PathParam("id") Integer id, User user) {

		UserService.updateUser(id, user);
		return "";
	}

	@POST
	@Path("/signIn")
	@Consumes(MediaType.APPLICATION_JSON)
	public String signIn(SignInRequest request) {
		UserService.signIn(request);
		return "User " + request.getUsername() + "has been logged in";

	}
	
	
	@GET
	@Path("/signOut/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public String signOut(@PathParam("username") String username) {
		
		UserService.signOut(username);
		return "User "+ username + " has been sign out";
	}
	
	@POST
	@Path("createProfile/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createProfile(@PathParam("username") String username, UserProfile userProfile) {
		
		UserService.createProfile(username, userProfile);
		
		return "User profile is created";
	}
	
	

}
