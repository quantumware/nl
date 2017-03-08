package nl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static List<Book> readBooksFromCSV(String fileName) {
        List<Book> books = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = "";//br.readLine();
            long i = 1;
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");

                Book book = createBook(attributes);
//                book.setId(i++);

                books.add(book);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return books;
    }
    
    private static Book createBook(String[] attrs) {
		Book book = new Book();
		book.setAuthor(attrs[0]);
		book.setTitle(attrs[1]);
		book.setIsbn(attrs[2]);
		return book;
    }

}
