package nl;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="BOOK")
@NamedQueries(value={
		@NamedQuery(name="byAuthor", lockMode=LockModeType.NONE,
				query="SELECT b.title FROM Book b WHERE b.author = :author"),
		@NamedQuery(name="allBooks", lockMode=LockModeType.NONE,
				query="SELECT b FROM Book b")
//				query="SELECT b FROM Book b JOIN b.persons p WHERE EXISTS (SELECT pb FROM PersonBook pb WHERE p = pb AND (pb.status='RETURNED' OR pb.status IS NULL)")
})
public class Book {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="ISBN", length=13)
	private String isbn;
	@Column(name="AUTHOR", length=255)
	private String author;
	@Column(name="TITLE", length=255)
	private String title;
	
	@JsonIgnore
	@OneToMany(mappedBy="book")
	private transient List<PersonBook> persons;
	
	public boolean addPerson(Person person, Status status, Timestamp affectedTimestamp) {
		PersonBook pb = new PersonBook();
		pb.setBook(this);
		pb.setPerson(person);
		pb.setStatus(status);
		pb.setAffectedTimestamp(new Timestamp(System.currentTimeMillis()));
		
		return persons.add(pb) && person.getBooks().add(pb);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public List<PersonBook> getPersons() {
		return persons;
	}
	public void setPersons(List<PersonBook> persons) {
		this.persons = persons;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (id != other.id)
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", isbn=" + isbn + ", author=" + author
				+ ", title=" + title + "]";
	}

}
