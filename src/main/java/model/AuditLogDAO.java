package model;

import entity.AuditLog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DAO para manejar registros de auditoría.
 * Permite insertar logs cuando se realizan acciones en otras tablas del sistema.
 */
public class AuditLogDAO {

    // -------------------------
    // ATRIBUTOS
    // -------------------------
    private EntityManagerFactory emf;

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public AuditLogDAO() {
        this.emf = Persistence.createEntityManagerFactory("DirectorioAutoresPU");
    }

    // -------------------------
    // MÉTODOS PRINCIPALES
    // -------------------------

    /**
     * Obtiene una nueva instancia de EntityManager.
     */
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Inserta un nuevo registro en la tabla de auditoría (AuditLog).
     *
     * @param actionType Tipo de acción realizada (INSERT, UPDATE, DELETE).
     * @param tableName  Nombre de la tabla afectada.
     * @param recordId   ID del registro afectado.
     */
    public void insertarLog(String actionType, String tableName, int recordId) {
        EntityManager em = getEntityManager();
        try {
            AuditLog log = new AuditLog();
            log.setActionType(actionType);
            log.setTableName(tableName);
            log.setRecordId(recordId);
            em.persist(log);
        } catch (Exception e) {
            System.out.println("Error al insertar log de auditoría: " + e.getMessage());
            e.printStackTrace(); // Imprimir stack completo para facilitar depuración
        } finally {
            em.close();
        }
    }
}
