package mainpackage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ArtifactRepository {

    private final Logger logger = LoggerFactory.getLogger(ArtifactRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    public List<Artifact> selectAll() {
        logger.info("{}()", "selectAll");
        var typedQuery = entityManager.createQuery("select a from Artifact a", Artifact.class);
        return typedQuery.getResultList();
    }

    @Transactional
    public void insert(Artifact artifact) {
        logger.info("{}()", "insert");
        entityManager.persist(artifact);
        logger.info("Record with id {} created", artifact.getId());
    }

    @Transactional
    public void update(int id, Artifact updated) {
        logger.info("{}()", "update");
        var artifactToUpdate = entityManager.find(Artifact.class, id);
        if (artifactToUpdate != null) {
            if (!updated.getGroupName().equals(artifactToUpdate.getGroupName())) {
                artifactToUpdate.setGroupName(updated.getGroupName());
            }
            if (!updated.getArtifactName().equals(artifactToUpdate.getArtifactName())) {
                artifactToUpdate.setArtifactName(updated.getArtifactName());
            }
            logger.info("Record with id {} updated", id);
        } else {
            logger.info("Record with id {} not found", id);
        }
    }

    @Transactional
    public void delete(int id) {
        logger.info("{}()", "delete");
        entityManager.remove(entityManager.getReference(Artifact.class, id));
        logger.info("Record with id {} deleted", id);
    }

    public void count() {
        logger.info("{}()", "count");
        var result = entityManager.createQuery("select count(*) from Artifact a", Long.class).getSingleResult();
        logger.info("{} records found", result);
    }

}
