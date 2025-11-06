package se.yrgo.controller;

import java.util.*;
import java.time.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import se.yrgo.domain.Book;

@Controller
public class BookController {
    private List<Book> bookList = new ArrayList<>();

    public BookController() {
        bookList.add(new Book("the three-body problem", "Liu Cixin", "sci-fi"));
        bookList.add(new Book("dune", "Frank Herbert", "sci-fi"));
        bookList.add(new Book("the hobbit", "J. R. R. Tolkien", "fantasy"));
        bookList.add(new Book("shadow and bone", "Leigh Bardugo", "fantasy"));
        bookList.add(new Book("atomic habits", "James Clear", "self-help / nonfiction"));
    }

    /**
     * Displays the home page and adds the current time to the model.
     * 
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String greating(Model model) {
        LocalDateTime date = LocalDateTime.now();
        model.addAttribute("Date", date);

        return "home";
    }

    /**
     * Returns all books.
     * 
     * @param model
     * @return
     */
    @GetMapping("/books")
    public String getBooks(Model model) {
        List<Book> books = List.copyOf(bookList);
        model.addAttribute("books", books);
        return "booklist";
    }

    /**
     * Shows either a list of genres (if no type is given)
     * or a list of books filtered by genre.
     * 
     * @param type
     * @return
     */
    @GetMapping("/genre")
    public ModelAndView getBooksByGenre(@RequestParam(required = false) String type) {
        ModelAndView mav = new ModelAndView("genre");

        Set<String> genres = new TreeSet<>();
        for (Book book : bookList) {
            genres.add(book.getGenre());
        }

        if (type == null) {
            mav.addObject("genres", genres);
            // genre
        } else if (!genres.contains(type)) {// om type är fel så vill vi se vad man kan välja
            mav.addObject("genres", genres);
            if (!type.isEmpty() || !type.equals("")) {
                mav.addObject("noMatch", true);
            }

        } else {
            // books
            List<Book> books = new ArrayList<>();
            for (Book book : bookList) {
                String genre = book.getGenre();
                if (genre.equals(type)) {
                    books.add(book);
                }
            }
            mav.addObject("books", books);
        }

        return mav;
    }

}
