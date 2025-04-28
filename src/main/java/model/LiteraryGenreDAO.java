package model;

import entity.LiteraryGenre;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * DAO para manejar operaciones CRUD de la entidad LiteraryGenre.
 */
@Named
@ApplicationScoped
public class LiteraryGenreDAO {

    // -------------------------
    // ATRIBUTOS
    // -------------------------

    @PersistenceContext(unitName = "DirectorioAutoresPU")
    private EntityManager em;

    // -------------------------
    // MÉTODOS PRINCIPALES
    // -------------------------

    /**
     * Obtener todos los géneros literarios.
     */
    public List<LiteraryGenre> getAllGenres() {
        return em.createQuery(
                "SELECT g FROM LiteraryGenre g", LiteraryGenre.class
        ).getResultList();
    }

    /**
     * Agregar un nuevo género literario.
     */
    @Transactional
    public void addGenre(LiteraryGenre genre) {
        em.persist(genre);
    }

    /**
     * Actualizar un género literario existente.
     */
    @Transactional
    public void updateGenre(LiteraryGenre genre) {
        em.merge(genre);
    }

    /**
     * Eliminar un género literario por su ID.
     */
    @Transactional
    public void deleteGenre(int id) {
        LiteraryGenre genre = em.find(LiteraryGenre.class, id);
        if (genre != null) {
            em.remove(genre);
        }
    }

    /**
     * Buscar un género literario por su ID.
     */
    public LiteraryGenre findGenreById(int id) {
        return em.find(LiteraryGenre.class, id);
    }
}
