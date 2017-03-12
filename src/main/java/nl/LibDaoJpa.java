package nl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LibDaoJpa implements LibDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(LibDaoJpa.class);

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("nl");
	private final EntityManager em = emf.createEntityManager();
	private final EntityTransaction tx = em.getTransaction();
	
	public boolean addBook(Book book) {
		try {
			tx.begin();
			em.persist(book);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		}
	}
	
	@Override
	public List<String> findByAuthor(String author) {
		Query query = em.createNamedQuery("byAuthor");
		query.setParameter("author", author);
		List<String> byStatus = query.getResultList();
		return byStatus;
	}

	@Override
	public List<Person> findAllPerson() {
		Query query = em.createNamedQuery("allPersons");
		List<Person> result = query.getResultList();
		return result;
	}

	@Override
	public List<Book> findAllBook() {
		Query query = em.createNamedQuery("allBooks");
		List<Book> result = query.getResultList();
		/*
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> query = cb.createQuery(Book.class);
		Root<Book> employee = query.from(Book.class);
		Join<PersonBook> project = employee.join("persons");
		Subquery<PersonBook> subquery = query.subquery(PersonBook.class);
		Root<PersonBook> subProject = query.from(PersonBook.class);
		subquery.where(cb.and(cb.equal(project, subProject), cb.or(cb.equal(subProject.get("status"), "RETURNED")), cb.isNull(subProject.get("status"))));
		query.where(cb.exists(subquery));
		*/
		return result;
	}

	@Override
	public List<Book> findBookByPerson(long personId) {
		List<Book> books = new ArrayList<>();
		Query query = em.createNamedQuery(PersonBook.NamedQuery.bookIdByPersonId.name());
		query.setParameter("personId", personId);
		List<Long> bookIds = query.getResultList();
		LOG.debug("bookIds:" + bookIds);
		for (long bookId : bookIds) {
			books.add(em.find(Book.class, bookId));
		}
		return books;
	}

	@Override
	public long addPerson(Person person) {
		try {
			tx.begin();
			em.persist(person);
			tx.commit();
			return person.getId();
		} catch (Exception e) {
			tx.rollback();
			return -1;
		}
	}

	@Override
	public boolean addObject(Object obj) {
		try {
			tx.begin();
			em.persist(obj);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		}
	}

	@Override
	public boolean lend(long personId, long bookId) {
		PersonBook pb = new PersonBook(personId, bookId);
		pb.setStatus(Status.LENT);
		try {
			tx.begin();
			em.persist(pb);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			return false;
		}
	}

	@Override
	public List<Book> findBooksByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
