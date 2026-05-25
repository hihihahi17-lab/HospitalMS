package hospital.exceptions;
public class EntityNotFoundException extends Exception {

    private final String entityType;
    private final int  entityId;

   
    public EntityNotFoundException(String entityType, int entityId) {
        super(entityType + " with ID [" + entityId + "] was not found.");
        this.entityType = entityType;
        this.entityId   = entityId;
    }

    
    public EntityNotFoundException(String message) {
        super(message);
        this.entityType = "Unknown";
        this.entityId   = -1;
    }

    public String getEntityType() { return entityType; }
    public int getEntityId()   { return entityId; }
}