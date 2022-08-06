package fr.m2i.api;

import java.sql.SQLException;
import java.util.Date;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import fr.m2i.models.News;

@Path("/news")
public class NewsService {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
	private EntityManager em;

	public NewsService() throws NamingException {
		this.em = factory.createEntityManager();
	}

	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNews(@FormParam("titre") String titre, @FormParam("description") String description)
			throws SQLException {

		News news = null;
		boolean success;

		try {
			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();

			news = new News();
			news.setTitre(titre);
			news.setDescription(description);
			news.setNews_date(new Date());

			success = false;

			try {
				em.persist(news);
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

		return Response.status(Status.OK).entity(news).build();
	}

	@PUT
	@Path("/id/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateNews(@FormParam("titre") String titre, @FormParam("description") String description,
			@PathParam("id") int id) throws SQLException {

		News news;
		boolean success;

		try {
			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();

			success = false;

			try {
				news = em.find(News.class, id);
				news.setTitre(titre);
				news.setDescription(description);
				success = true;
			} finally {
				if (success) {
					entityTransaction.commit();
				} else {
					entityTransaction.rollback();
				}
			}

		} finally {
			em.close();
		}

		return Response.status(Status.OK).build();
	}

	@DELETE
	@Path("/id/{id}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id) throws SQLException {

		News news;
		boolean success;

		try {
			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();

			success = false;

			try {
				news = em.find(News.class, id);
				em.remove(news);
				success = true;
			} finally {
				if (success) {
					entityTransaction.commit();
				} else {
					entityTransaction.rollback();
				}
			}

		} finally {
			em.close();
		}
		return Response.status(Status.OK).build();
	}
}
