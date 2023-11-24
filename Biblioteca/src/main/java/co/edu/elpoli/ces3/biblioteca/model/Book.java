package co.edu.elpoli.ces3.biblioteca.model;

import co.edu.elpoli.ces3.biblioteca.dto.DtoBook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Book extends Conexion implements CRUD{
    public int id;

    protected String titulo;

    private String autor;

    public Book(int id, String titulo, String autor){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }

    public Book(String titulo){
        this.titulo = titulo;
    }

    public Book() {
    }

    public int getId(){
        return this.id;
    }


    private void setId(int id){
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "El libro se llama: " + this.titulo +
                " su autor es: " + this.autor;
    }

    @Override
    public Book create(DtoBook book) throws SQLException {
        Connection cnn = this.getConexion();
        if(cnn != null) {
            String sql = "INSERT INTO libro(titulo, autor) VALUES('"+book.getTitulo()+"', '"+book.getAutor()+"')";
            this.titulo = book.getTitulo();
            this.autor = book.getAutor();
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                this.id = rs.getInt(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                cnn.close();
            }
            return this;
        }
        return null;
    }

    @Override
    public ArrayList<Book> all() {
        Connection cnn = this.getConexion();
        ArrayList<Book> books = new ArrayList<>();

        if (cnn != null) {
            String sql = "SELECT * FROM libro";
            try {
                PreparedStatement stmt = cnn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String autor = rs.getString("autor");
                    String titulo = rs.getString("titulo");
                    Book book = new Book(id, autor, titulo);
                    books.add(book);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (cnn != null) {
                        cnn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return books;
        }
        return null;
    }



    @Override
    public Book findById(int bookId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "SELECT * FROM libro WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, bookId);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        String titulo = rs.getString("titulo");
                        String autor = rs.getString("autor");
                        return new Book(id, titulo, autor);
                    } else {
                        return null;
                    }
                }
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return null;
    }

    @Override
    public Book update(Book book) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            // Puedes ajustar esta consulta SQL según tus necesidades específicas
            String sql = "UPDATE libro SET titulo = ?, autor = ? WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitulo());
                stmt.setString(2, book.getAutor());
                stmt.setInt(3, book.getId());
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
        return book;
    }

    @Override
    public void delete(int bookId) throws SQLException {
        Connection cnn = getConexion();

        if (cnn != null) {
            String sql = "DELETE FROM libro WHERE id = ?";
            try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
            } finally {
                if (cnn != null) {
                    cnn.close();
                }
            }
        }
    }


}
