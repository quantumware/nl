package nl;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibDaoJpaTest {
	private static final Logger LOG = LoggerFactory.getLogger(LibDaoJpaTest.class);

	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown() {
		
	}
	
//	@Test
	public void test() {
		LibDao libDao = new LibDaoJpa();
		
		String clazzPath = LibDaoJpaTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String newPath = clazzPath.substring(0, clazzPath.lastIndexOf("target")) + "data/LibData.csv";
		System.out.println(newPath);
		
		List<Book> books = DataLoader.readBooksFromCSV(newPath);
		for (Book book : books) {
			libDao.addBook(book);
		}
		
		List<String> titles = libDao.findByAuthor("Jules Verne");
		System.out.println(titles);
		Assert.assertTrue(titles.contains("Twenty Thousand Leagues Under the Sea"));
		
		List<Book> allBooks = libDao.findAllBook();
		Assert.assertEquals(34, allBooks.size());
		
		Person person = new Person();
		person.setName("user1");
		person.setPhoneNumber("02123456678");
		person.setEmailAddress("use1@nla");
		
		long personId = libDao.addPerson(person);
		LOG.debug("personId:" + personId);
		
		List<Person> allPersons = libDao.findAllPerson();
		LOG.debug("allPerson:" + allPersons);
		Assert.assertEquals(1, allPersons.size());
		
		long bookId = allBooks.get(0).getId();
		libDao.lend(personId, bookId);
		
		List<Book> mybooks = libDao.findBookByPerson(person.getId());
		LOG.debug(allBooks.get(0) + ", " + mybooks.get(0));
		Assert.assertEquals(bookId, mybooks.get(0).getId());
	}

}
