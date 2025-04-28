package model;

import entity.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * DAO para manejar operaciones CRUD de la entidad Author.
 */
@Named
@ApplicationScoped
public class AuthorDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    // -------------------------
    // INYECCIÓN DEL ENTITY MANAGER
    // -------------------------
    @PersistenceContext(unitName = "DirectorioAutoresPU")
    private EntityManager em;

    // -------------------------
    // MÉTODOS CRUD
    // -------------------------

    /**
     * Obtener todos los autores activos.
     */
    public List<Author> getAllAuthors() {
        return em.createQuery(
                "SELECT a FROM Author a WHERE a.status = 'ACTIVE'", Author.class
        ).getResultList();
    }

    /**
     * Agregar un nuevo autor.
     */
    @Transactional
    public void addAuthor(Author author) {
        em.persist(author);
    }

    /**
     * Actualizar un autor existente.
     */
    @Transactional
    public void updateAuthor(Author author) {
        em.merge(author);
    }

    /**
     * Eliminar lógicamente un autor (cambia su estado a INACTIVE).
     */
    @Transactional
    public void deleteAuthor(int idAuthor) {
        Author author = em.find(Author.class, idAuthor);
        if (author != null) {
            author.setStatus("INACTIVE");
            em.merge(author);
        }
    }

    /**
     * Buscar un autor por su ID.
     */
    public Author findAuthorById(int idAuthor) {
        return em.find(Author.class, idAuthor);
    }

    /**
     * Buscar autores por nombre (búsqueda parcial).
     */
    public List<Author> findAuthorsByName(String name) {
        return em.createQuery(
                        "SELECT a FROM Author a WHERE a.authorName LIKE :name AND a.status = 'ACTIVE'", Author.class
                ).setParameter("name", "%" + name + "%")
                .getResultList();
    }

    /**
     * Filtrar autores por el ID de género.
     */
    public List<Author> filterAuthorsByGenre(int genreId) {
        return em.createQuery(
                        "SELECT a FROM Author a WHERE a.idGenre = :genreId AND a.status = 'ACTIVE'", Author.class
                ).setParameter("genreId", genreId)
                .getResultList();
    }

    /**
     * Contar la cantidad de autores activos.
     */
    public long countAuthors() {
        return em.createQuery(
                "SELECT COUNT(a) FROM Author a WHERE a.status = 'ACTIVE'", Long.class
        ).getSingleResult();
    }
}
