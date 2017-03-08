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
@Table(name="PERSON")
@NamedQueries(value={
		@NamedQuery(name="allPersons", lockMode=LockModeType.NONE,
				query="SELECT p FROM Person p")
})
public class Person {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="NAME", length=255)
	private String name;
	@Column(name="PHONE_NUMBER", length=15)
	private String phoneNumber;
	@Column(name="EMAIL_ADDRESS", length=255)
	private String emailAddress;
	
	@JsonIgnore
	@OneToMany(mappedBy="person")
	private List<PersonBook> books;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public List<PersonBook> getBooks() {
		return books;
	}
	
	public boolean addBook(Book book, Status status) {
		PersonBook pb = new PersonBook();
		pb.setBook(book);
		pb.setPerson(this);
		pb.setStatus(status);
		pb.setAffectedTimestamp(new Timestamp(System.currentTimeMillis()));
		
		return books.add(pb) && book.getPersons().add(pb);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
		Person other = (Person) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", emailAddress=" + emailAddress + "]";
	}

}
