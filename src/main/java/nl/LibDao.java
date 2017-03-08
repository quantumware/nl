package nl;

import java.util.List;

public interface LibDao {
	public List<Person> findAllPerson();
	public List<Book> findAllBook();
	public List<Book> findBookByPerson(long personId);
	
	public long addPerson(Person person);
	public boolean addBook(Book book);
	public boolean lend(long personId, long bookId);
	public boolean addObject(Object obj);
	
	public List<String> findByAuthor(String author);
	public List<Book> findBooksByAuthor(String author);
}
