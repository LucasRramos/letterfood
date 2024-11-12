import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.letterfood.config.MongoConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.mongodb.client.model.Filters.eq;

public abstract class BaseRepository<T> {
    private final MongoCollection<T> collection;

    protected BaseRepository(MongoConfig mongoConfig, String collectionName, Class<T> entityClass) {
        this.collection = mongoConfig.getDatabase().getCollection(collectionName, entityClass);
    }

    public void save(T entity) {
        collection.insertOne(entity);
    }

    public Optional<T> findById(String id) {
        return Optional.ofNullable(collection.find(eq("_id", id)).first());
    }

    public List<T> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    // Método para encontrar um documento por qualquer campo específico
    public Optional<T> findByField(String fieldName, Object value) {
        return Optional.ofNullable(collection.find(eq(fieldName, value)).first());
    }
}
