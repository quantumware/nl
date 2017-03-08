package nl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nl")
public class LibController {

	@Autowired
	private LibDao libDao;
	
	@RequestMapping("/loaddata")
	public String loadData() {
		String clazzPath = LibController.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String newPath = clazzPath.substring(0, clazzPath.lastIndexOf("target")) + "data/LibData.csv";
		List<Book> books = DataLoader.readBooksFromCSV(newPath);
		for (Book book : books) {
			libDao.addBook(book);
		}
		return "DONE";
	}
    
    @RequestMapping("/")
    public Map<String,Object> index() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("date", new Date().toString());
		model.put("content", "Greetings from MY");
		return model;
    }
    
    @RequestMapping("/books")
    public @ResponseBody List<Book> books() {
    	return libDao.findAllBook();
    }
    
    @RequestMapping("/addperson")
    public @ResponseBody long addPerson(@RequestParam(name="name") String name,
    		@RequestParam(name="phoneNumber") String phoneNumber, @RequestParam(name="emailAddress") String emailAddress) {
    	Person person = new Person();
    	person.setName(name);
    	person.setPhoneNumber(phoneNumber);
    	person.setEmailAddress(emailAddress);
    	return libDao.addPerson(person);
    }
    
    @RequestMapping("/persons")
    public @ResponseBody List<Person> persons() {
    	return libDao.findAllPerson();
    }
    
    @RequestMapping("/lend/{personId}/{bookId}")
    public @ResponseBody Boolean lend(@PathVariable long personId, @PathVariable long bookId) {
    	return libDao.lend(personId, bookId);
    }
    
    @RequestMapping("/booklent/{personId}")
    public @ResponseBody List<Book> booklent(@PathVariable long personId) {
    	return libDao.findBookByPerson(personId);
    }
    
}
