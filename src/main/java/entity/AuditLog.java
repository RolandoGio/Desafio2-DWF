package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidad que representa la tabla AuditLog en la base de datos.
 */
@Entity
@Table(name = "AuditLog")
public class AuditLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_log;

    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;

    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Column(name = "record_id", nullable = false)
    private int recordId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "action_date", nullable = false)
    private Date actionDate = new Date(); // Se pone automáticamente la fecha actual

    // Constructor vacío (requerido por JPA)
    public AuditLog() {
    }

    // Constructor completo (opcional si quieres usarlo)
    public AuditLog(String actionType, String tableName, int recordId) {
        this.actionType = actionType;
        this.tableName = tableName;
        this.recordId = recordId;
        this.actionDate = new Date();
    }

    // Getters y Setters
    public int getId_log() {
        return id_log;
    }

    public void setId_log(int id_log) {
        this.id_log = id_log;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
}
