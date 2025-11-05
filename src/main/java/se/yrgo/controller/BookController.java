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

    
    @GetMapping("/home")
    public String greating(Model model) {
        LocalDateTime date = LocalDateTime.now();
        model.addAttribute("Date", date);

        return "home";
    }

    /**
     * 
    */
    @GetMapping("/books")
    public String getBooks(Model model) {
        //Do not want to reffrens the real bookList. therefor it's List.copyOf(bookList)
        //Could also do something like this 
        //List.of(Collections.unmodifiableList(bookList));
        //but i do not want to have a "living" list.
        List<Book> books = List.copyOf(bookList);
        model.addAttribute("books", books);

        return "booklist";
    }

    @GetMapping("/genre")
    public String getBooksByGenre(@RequestParam(required = false) String type, Model model) {
        // Välja en genre och få en lista med böcker inom den genren.
        if(type == null){
            //genre
            Set<String> genres = new TreeSet<>();
            for (Book book : bookList) {
                genres.add(book.getGenre());
            }
            model.addAttribute("genres", genres);
        }else{
            //books
            List<Book> books = new ArrayList<>();
            for (Book book : bookList) {
                String genre = book.getGenre();
                if(genre.equals(type)){
                    books.add(book);
                }
            }
            model.addAttribute("books", books);
        }
        

        return "genre";
    }

}
