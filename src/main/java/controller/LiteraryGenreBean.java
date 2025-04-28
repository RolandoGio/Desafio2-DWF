package controller;

import entity.LiteraryGenre;
import model.LiteraryGenreDAO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * ManagedBean para manejar operaciones CRUD de géneros literarios en la vista JSF.
 */
@Named("genreBean")
@ViewScoped
public class LiteraryGenreBean implements Serializable {

    private LiteraryGenre genre;
    private List<LiteraryGenre> genres;

    @Inject
    private LiteraryGenreDAO genreDAO;

    @PostConstruct
    public void init() {
        genre = new LiteraryGenre();
        loadGenres();
    }

    @Transactional
    public void addGenre() {
        try {
            genreDAO.addGenre(genre);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Género agregado correctamente."));
            genre = new LiteraryGenre();
            loadGenres();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el género."));
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateGenre() {
        try {
            genreDAO.updateGenre(genre);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Género actualizado correctamente."));
            genre = new LiteraryGenre();
            loadGenres();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el género."));
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteGenre() {
        try {
            genreDAO.deleteGenre(genre.getIdGenre());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Género eliminado correctamente."));
            genre = new LiteraryGenre();
            loadGenres();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el género."));
            e.printStackTrace();
        }
    }

    public void loadGenres() {
        genres = genreDAO.getAllGenres();
    }

    /**
     * Selecciona un género de la tabla para editar.
     */
    public void selectGenre(LiteraryGenre g) {
        if (g != null) {
            this.genre = g;
        }
    }

    public LiteraryGenre getGenre() {
        return genre;
    }

    public void setGenre(LiteraryGenre genre) {
        this.genre = genre;
    }

    public List<LiteraryGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<LiteraryGenre> genres) {
        this.genres = genres;
    }
}
