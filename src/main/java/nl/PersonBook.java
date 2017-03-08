package nl;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.LockModeType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PERSON_BOOK")
@IdClass(PersonBookId.class)
@NamedQueries(value={
		@NamedQuery(name="bookIdByPersonId", lockMode=LockModeType.NONE,
				query="SELECT pb.bookId FROM PersonBook pb WHERE pb.personId = :personId"),
})
public class PersonBook {
	
	public static enum NamedQuery {
		bookIdByPersonId
	}

	@Id
	@Column(name="PERSON_ID")
	private long personId;
	@Id
	@Column(name="BOOK_ID")
	private long bookId;
	
	@Column(name="STATUS")
	private Status status;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="AFFECTED_TIMESTAMP")
	private Date affectedTimestamp;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="PERSON_ID", referencedColumnName="ID")
	private Person person;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="BOOK_ID", referencedColumnName="ID")
	private Book book;
	
	public PersonBook() {
	}

	public PersonBook(long personId, long bookId) {
		this.personId = personId;
		this.bookId = bookId;
	}

	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public Date getAffectedTimestamp() {
		return affectedTimestamp;
	}
	public void setAffectedTimestamp(Date affectedTimestamp) {
		this.affectedTimestamp = affectedTimestamp;
	}

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
		this.personId = person.getId();
	}

	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
		this.bookId = book.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((affectedTimestamp == null) ? 0 : affectedTimestamp
						.hashCode());
		result = prime * result + (int) (bookId ^ (bookId >>> 32));
		result = prime * result + (int) (personId ^ (personId >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		PersonBook other = (PersonBook) obj;
		if (affectedTimestamp == null) {
			if (other.affectedTimestamp != null)
				return false;
		} else if (!affectedTimestamp.equals(other.affectedTimestamp))
			return false;
		if (bookId != other.bookId)
			return false;
		if (personId != other.personId)
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PersonBook [personId=" + personId + ", bookId=" + bookId
				+ ", status=" + status + ", affectedTimestamp="
				+ affectedTimestamp + "]";
	}

}
