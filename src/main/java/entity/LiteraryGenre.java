package entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entidad que representa la tabla LiteraryGenre en la base de datos.
 */
@Entity
@Table(name = "LiteraryGenre")
public class LiteraryGenre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_genre;

    @Column(name = "genre_name", nullable = false, unique = true, length = 100)
    private String genreName;

    // Constructor vacío (requerido por JPA)
    public LiteraryGenre() {
    }

    // Constructor completo (opcional para crear objetos rápidamente)
    public LiteraryGenre(String genreName) {
        this.genreName = genreName;
    }

    // Getters y Setters corregidos para JSF
    public int getIdGenre() {
        return id_genre;
    }

    public void setIdGenre(int idGenre) {
        this.id_genre = idGenre;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
