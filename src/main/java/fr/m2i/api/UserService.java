package fr.m2i.api;

import java.sql.SQLException;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.m2i.models.User;

@Path("/user")
public class UserService {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
	private EntityManager em;

	public UserService() throws NamingException {
		this.em = factory.createEntityManager();
	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(@FormParam("username") String username, @FormParam("password") String password)
			throws SQLException {

		User user = null;
		boolean success;

		try {
			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();

			user = new User();
			user.setUsername(username);
			user.setPassword(password);

			success = false;

			try {
				em.persist(user);
				success = true;
			} finally {
				if (success) {
					entityTransaction.commit();
				} else {
					entityTransaction.rollback();
				}
			}

		} finally {
//				em.refresh(task2);
			em.close();
		}

		return Response.status(Status.OK).entity(user).build();
	}
}
