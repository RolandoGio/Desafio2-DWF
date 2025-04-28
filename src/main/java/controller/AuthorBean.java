package controller;

import entity.Author;
import entity.LiteraryGenre;
import model.AuthorDAO;
import model.LiteraryGenreDAO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

/**
 * ManagedBean para gestionar autores en el directorio de autores.
 */
@Named("authorBean")
@ViewScoped
public class AuthorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // -------------------------
    // ATRIBUTOS
    // -------------------------
    private Author newAuthor;
    private Author selectedAuthor;
    private List<Author> authors;
    private List<LiteraryGenre> genreList;
    private int genreFilterId;
    private String nameFilter;
    private long authorCount;

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private LiteraryGenreDAO literaryGenreDAO;

    // -------------------------
    // MÉTODOS DE INICIALIZACIÓN
    // -------------------------

    @PostConstruct
    public void init() {
        newAuthor = new Author();
        selectedAuthor = new Author();
        loadAuthors();
        loadGenres();
        countAuthors();
    }

    // -------------------------
    // MÉTODOS CRUD
    // -------------------------

    public void addAuthor() {
        try {
            authorDAO.addAuthor(newAuthor);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Autor agregado correctamente."));
            newAuthor = new Author();
            loadAuthors();
            countAuthors();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al agregar autor."));
            e.printStackTrace();
        }
    }

    public void updateAuthor() {
        try {
            if (selectedAuthor != null && selectedAuthor.getId_author() != 0) {
                authorDAO.updateAuthor(selectedAuthor);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Autor actualizado correctamente."));
                selectedAuthor = new Author();
                loadAuthors();
                countAuthors();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al actualizar autor."));
            e.printStackTrace();
        }
    }

    public void deleteAuthor() {
        try {
            if (selectedAuthor != null && selectedAuthor.getId_author() != 0) {
                authorDAO.deleteAuthor(selectedAuthor.getId_author());
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Autor eliminado correctamente."));
                selectedAuthor = new Author();
                loadAuthors();
                countAuthors();
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al eliminar autor."));
            e.printStackTrace();
        }
    }

    // -------------------------
    // FILTROS Y BÚSQUEDA
    // -------------------------

    public void searchByName() {
        if (nameFilter != null && !nameFilter.trim().isEmpty()) {
            authors = authorDAO.findAuthorsByName(nameFilter);
        } else {
            loadAuthors();
        }
    }

    public void filterByGenre() {
        if (genreFilterId > 0) {
            authors = authorDAO.filterAuthorsByGenre(genreFilterId);
        } else {
            loadAuthors();
        }
    }

    public void countAuthors() {
        authorCount = authorDAO.countAuthors();
    }

    // -------------------------
    // CARGAR DATOS
    // -------------------------

    public void loadAuthors() {
        authors = authorDAO.getAllAuthors();
    }

    public void loadGenres() {
        genreList = literaryGenreDAO.getAllGenres();
    }

    // -------------------------
    // MÉTODOS AUXILIARES
    // -------------------------

    public void selectAuthor(Author a) {
        if (a != null) {
            selectedAuthor = a;
        }
    }

    public String getGenreNameById(int idGenre) {
        if (genreList != null) {
            for (LiteraryGenre genre : genreList) {
                if (genre.getIdGenre() == idGenre) {
                    return genre.getGenreName();
                }
            }
        }
        return "Desconocido";
    }

    // -------------------------
    // GETTERS Y SETTERS
    // -------------------------

    public Author getNewAuthor() {
        return newAuthor;
    }

    public void setNewAuthor(Author newAuthor) {
        this.newAuthor = newAuthor;
    }

    public Author getSelectedAuthor() {
        return selectedAuthor;
    }

    public void setSelectedAuthor(Author selectedAuthor) {
        this.selectedAuthor = selectedAuthor;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<LiteraryGenre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<LiteraryGenre> genreList) {
        this.genreList = genreList;
    }

    public int getGenreFilterId() {
        return genreFilterId;
    }

    public void setGenreFilterId(int genreFilterId) {
        this.genreFilterId = genreFilterId;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public long getAuthorCount() {
        return authorCount;
    }

    public void setAuthorCount(long authorCount) {
        this.authorCount = authorCount;
    }
}
