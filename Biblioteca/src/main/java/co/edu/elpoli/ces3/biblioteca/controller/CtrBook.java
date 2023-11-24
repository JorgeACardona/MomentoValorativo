package co.edu.elpoli.ces3.biblioteca.controller;

import co.edu.elpoli.ces3.biblioteca.dto.DtoBook;
import co.edu.elpoli.ces3.biblioteca.model.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public class CtrBook {

    private Book modelBook;

    public CtrBook(){
        modelBook = new Book();
    }

    public DtoBook addBook(DtoBook book){
        try {
            Book newBook = modelBook.create(book);
            return new DtoBook(newBook.getId(), newBook.getTitulo(), newBook.getAutor());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DtoBook> getAllBooks() {
        try {
            ArrayList<Book> books = modelBook.all(); // Llama al método 'all' del modelo
            ArrayList<DtoBook> dtoBooks = new ArrayList<>();

            for (Book book : books) {
                DtoBook dtoBook = new DtoBook(
                        book.getId(),
                        book.getTitulo(),
                        book.getAutor()
                );
                dtoBooks.add(dtoBook);
            }

            return dtoBooks;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DtoBook getBookById(int bookId) {
        try {
            Book book = modelBook.findById(bookId);
            if (book != null) {
                return new DtoBook(book.getId(), book.getTitulo(), book.getAutor());
            } else {
                throw new RuntimeException("Libro no encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DtoBook updateBook(int bookId, DtoBook updatedBook) {
        try {
            Book book = new Book(
                    bookId,
                    updatedBook.getTitulo(),
                    updatedBook.getAutor()

            );

            Book updated = modelBook.update(book);
            return new DtoBook(updated.getId(), updated.getTitulo(), updated.getAutor());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(int bookId) {
        try {
            modelBook.delete(bookId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
