package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que representa un autor en el sistema.
 */
@Entity
@Table(name = "Author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_author;

    @Column(name = "author_name", nullable = false, unique = true)
    private String authorName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "phone", nullable = false, length = 8)
    private String phone;

    @Column(name = "id_genre", nullable = false)
    private int idGenre;

    @Column(name = "status", nullable = false, length = 10)
    private String status = "ACTIVE"; // Valor por defecto

    // Constructor vacío requerido por JPA
    public Author() {
    }

    // --- Getters y Setters ---

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
