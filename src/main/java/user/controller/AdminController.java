package main.java.user.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.java.user.entity.User;
import main.java.user.entity.UserProfile;
import main.java.user.entity.UserResponse;
import main.java.user.services.AdminService;

@Path("/admin")
public class AdminController {

	//TODO
	//1. Delete user - only admin can delete user
	//2. Verify user - only admin can verify user
	//3. Get all users that are not verified - only admin can access to list with all unverified users
	//4. Get all users - list of users with user profiles with custom response message
	
	@GET
	@Path("/getAllUnverified/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUnverified(@PathParam("username") String username) {
		
		return AdminService.getAllUnverified(username);
		
	}
	
	
	@GET
	@Path("/getUnverified/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUnverified(@PathParam("username") String username){
		
		return AdminService.getUnverified(username);
	}
	
	
	@GET
	@Path("/getUnverifiedList/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserResponse> getList(@PathParam("username") String username){
		
		return AdminService.getList(username);
		
	}
	
	
	@GET
	@Path("/getUserProfiles/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserProfile> getUserProfiles(@PathParam("username") String username){
		
		return AdminService.getUserProfiles(username);
		
		
	}
	
	
	
}
