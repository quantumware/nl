package nl;

import java.io.Serializable;

public class PersonBookId implements Serializable {

  private long personId;

  private long bookId;
  
  public PersonBookId(long personId, long bookId) {
	  this.personId = personId;
	  this.bookId = bookId;
  }

public int hashCode() {
    return (int)(personId + bookId);
  }

  public boolean equals(Object object) {
    if (object instanceof PersonBookId) {
    	PersonBookId otherId = (PersonBookId) object;
      return (otherId.personId == this.personId) && (otherId.bookId == this.bookId);
    }
    return false;
  }

}
