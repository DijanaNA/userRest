package main.java.user.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import main.java.user.entity.SignInRequest;
import main.java.user.entity.User;
import main.java.user.entity.UserProfile;
import main.java.user.entity.UserResponse;

public class AdminService {
	static SessionFactory factory;

	public static Session init() {

		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(main.java.user.entity.Address.class);
		cfg.addAnnotatedClass(main.java.user.entity.User.class);
		cfg.addAnnotatedClass(main.java.user.entity.UserProfile.class);
		cfg.configure();

		factory = cfg.configure().buildSessionFactory();
		Session session = factory.openSession();
		return session;

	}

	public static void createUser(User user) {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			Date date = new Date(System.currentTimeMillis());
			user.setCreatedOn(date);

			session.save(user);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	public static void updateUser(Integer id, User user) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();
			Date date = new Date(System.currentTimeMillis());

			User userOldParameters = session.get(User.class, id);
			userOldParameters.setPassword(user.getPassword());
			userOldParameters.setCreatedOn(date);
			userOldParameters.setEmail(user.getEmail());

			session.update(userOldParameters);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	public static void signIn(SignInRequest request) {

		User user = validateUser(request);

		if (user != null) {
			Session session = init();
			Transaction tx = null;

			try {

				session = factory.openSession();
				tx = session.beginTransaction();

				User userOldParameters = session.get(User.class, user.getId());

				userOldParameters.setSignedIn(true);

				session.update(userOldParameters);

				tx.commit();
				session.close();

			} catch (Exception e) {
				tx.rollback();
				System.out.println(e);
			}

		} else {
			System.out.println("Username or password is invalid");

		}

	}

	private static User validateUser(SignInRequest request) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();

			Query query = session.createQuery(
					"SELECT k FROM main.java.user.entity.User k WHERE k.username = :username AND k.password = :password");//
			query.setParameter("username", request.getUsername());
			query.setParameter("password", request.getPassword());

			User user = (User) query.getResultList().get(0);

			tx.commit();
			session.close();

			return user;

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;
	}

	public static void signOut(String username) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();

			// get logged in user
			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username ");
			query.setParameter("username", username);

			User user = (User) query.getResultList().get(0);

			// Step by step
			// List<User> rs = query.getResultList();
			// User user = rs.get(0);

			// update user
			user.setSignedIn(false);
			session.update(user);

			tx.commit();
			session.close();

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

	}

	public static List<User> getAllUnverified(String username) {
		// TODO Auto-generated method stub

		if (checkIfAdmin(username)) {

			return getAllNotVerifiedUsers();

		} else {
			System.out.println("User is not administrator");
			return null;
		}

	}

	private static List<User> getAllNotVerifiedUsers() {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();

			// get logged in user
			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.verified=:verified ");
			query.setParameter("verified", false);

			List<User> users = query.getResultList();

			tx.commit();
			session.close();
			return users;

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
			return null;
		}
	}

	private static boolean checkIfAdmin(String username) {
		Session session = init();
		Transaction tx = null;

		try {

			session = factory.openSession();
			tx = session.beginTransaction();

			// get logged in user
			Query query = session
					.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username AND k.isAdmin=:isAdmin");
			query.setParameter("username", username);
			query.setParameter("isAdmin", true);

			User user = (User) query.getResultList().get(0);

			// Step by step
			// List<User> rs = query.getResultList();
			// User user = rs.get(0);
			tx.commit();
			session.close();

			if (user != null) {
				return true;
			}

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
		return false;
	}

	public static List<User> getUnverified(String username) {

		if (isAdministrator(username)) {
			return getUnverifiedUsers();
		} else {
			System.out.println("User is not admin");
		}

		return null;

	}

	private static List<User> getUnverifiedUsers() {
		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.verified=:verified");
			query.setParameter("verified", false);

			List<User> res = query.getResultList();

			tx.commit();
			session.close();

			return res;
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;

	}

	private static boolean isAdministrator(String username) {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			// get logged in user
			Query query = session
					.createQuery("FROM main.java.user.entity.User k WHERE k.username=:username AND k.isAdmin=:isAdmin");
			query.setParameter("username", username);
			query.setParameter("isAdmin", true);

			User user = (User) query.getResultList().get(0);

			tx.commit();
			session.close();

			if (user != null) {
				return true;
			}

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}
		return false;

	}

	public static List<UserResponse> getList(String username) {
		// TODO Auto-generated method stub
		if (isAdministrator(username))
			return getUnverifiedUsernames();

		else {
			System.out.println("User is not admin");
			return null;
		}

	}

	private static List<UserResponse> getUnverifiedUsernames() {

		Session session = init();
		Transaction tx = null;

		List<UserResponse> result = new ArrayList<>();

		try {

			tx = session.beginTransaction();

			Query query = session.createQuery("FROM main.java.user.entity.User k WHERE k.verified=:verified");
			query.setParameter("verified", false);

			List<User> res = query.getResultList();

			for (User user : res) {

				UserResponse response = new UserResponse();
				response.setId(user.getId());
				response.setUsername(user.getUsername());

				result.add(response);

			}

			tx.commit();
			session.close();

			return result;
		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;
	}

	public static List<UserProfile> getUserProfiles(String username) {

		if (isAdministrator(username))
			return getAllUserProfiles();
		else {
			System.out.println("User is not admin");
			return null;
		}

	}

	private static List<UserProfile> getAllUserProfiles() {

		Session session = init();
		Transaction tx = null;

		try {

			tx = session.beginTransaction();

			Query query = session.createQuery("SELECT p FROM main.java.user.entity.UserProfile p ");
			List<UserProfile> res = query.getResultList();

			tx.commit();
			session.close();
			return res;

		} catch (Exception e) {
			tx.rollback();
			System.out.println(e);
		}

		return null;
	}

	// create user profile
	// 1. url parameter username
	// 2. get User by username from database in User object
	// 3. set User in userProfile constructor
	// 4. save user profile

}
