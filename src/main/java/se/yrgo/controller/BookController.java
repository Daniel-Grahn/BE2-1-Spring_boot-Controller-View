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
     * Renders the current time to the view.
     *
     * @param model model used to pass data to the view
     * @return the view name "home"
     */
    @GetMapping("/home")
    public String greating(Model model) {
        LocalDateTime date = LocalDateTime.now();
        model.addAttribute("Date", date);

        return "home";
    }

     /**
     * Returns a view that displays all books.
     *
     * @param model model used to pass data to the view
     * @return the view name "booklist"
     */
    @GetMapping("/books")
    public String getBooks(Model model) {
        List<Book> books = List.copyOf(bookList);
        model.addAttribute("books", books);
        return "booklist";
    }

    /**
     * Displays either a list of available genres (if no type is provided)
     * or a list of books filtered by a specific genre.
     *
     * If the user provides a genre that does not exist,
     * the view will return the available genres and set "noMatch" to true.
     *
     * @param type genre filter (optional)
     * @return a ModelAndView pointing to the "genre" view
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
        } else if (!genres.contains(type)) {//type has no match genres 
            mav.addObject("genres", genres);
            if (!type.isEmpty() || !type.equals("")) {
                mav.addObject("noMatch", true);
            }

        } else {
            // recommended books
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
