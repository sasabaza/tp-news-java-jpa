package fr.m2i.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.m2i.models.News;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PAGE="/WEB-INF/pages/accueil.jsp";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccueilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("lastnews", this.lastNews());
		
		this.getServletContext().getRequestDispatcher(PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	@SuppressWarnings("unchecked")
	protected List<News> lastNews() {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnityPersist");
		EntityManager em = factory.createEntityManager();
		
		List<News> listNews;
		boolean success;
		
		try {
			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();
			
			success = false;
			
			try {
				listNews = em.createNativeQuery("select * from news_table order by news_date desc limit 0, 5", News.class).getResultList();
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
		
		return listNews;
	}
}
