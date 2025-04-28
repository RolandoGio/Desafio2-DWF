package controller;

import entity.AuditLog;
import model.AuditLogDAO;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * ManagedBean para manejar operaciones de registro en la auditoría (AuditLog).
 */
@Named("auditLogBean")
@ViewScoped
public class AuditLogBean implements Serializable {

    // -------------------------
    // ATRIBUTOS
    // -------------------------
    private AuditLogDAO auditLogDAO;
    private AuditLog auditLog;

    // -------------------------
    // MÉTODOS DE INICIALIZACIÓN
    // -------------------------

    /**
     * Inicializa el Bean creando instancias de DAO y entidad AuditLog.
     */
    @PostConstruct
    public void init() {
        auditLogDAO = new AuditLogDAO();
        auditLog = new AuditLog();
    }

    // -------------------------
    // MÉTODOS PRINCIPALES
    // -------------------------

    /**
     * Inserta un nuevo registro en la tabla de auditoría.
     *
     * @param actionType Tipo de acción realizada (INSERT, UPDATE, DELETE).
     * @param tableName  Nombre de la tabla donde ocurrió la acción.
     * @param recordId   ID del registro afectado.
     */
    @Transactional
    public void insertLog(String actionType, String tableName, int recordId) {
        try {
            auditLogDAO.insertarLog(actionType, tableName, recordId);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Éxito!", "Registro de auditoría insertado correctamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo insertar el registro de auditoría."));
            e.printStackTrace();
        }
    }

    // -------------------------
    // GETTERS Y SETTERS
    // -------------------------

    public AuditLog getAuditLog() {
        return auditLog;
    }

    public void setAuditLog(AuditLog auditLog) {
        this.auditLog = auditLog;
    }
}
