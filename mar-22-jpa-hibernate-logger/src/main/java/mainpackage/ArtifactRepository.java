package mainpackage;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ArtifactRepository {

    private final Logger logger = LoggerFactory.getLogger(ArtifactRepository.class);

    public List<Artifact> selectAll() {
        logger.info("{}()", "selectAll");
        var entityManager = Main.factory.createEntityManager();
        entityManager.getTransaction().begin();
        var artifacts = entityManager.createQuery("select a from Artifact a", Artifact.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return artifacts;
    }

    public void insert(Artifact artifact) {
        logger.info("{}()", "insert");
        var entityManager = Main.factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(artifact);
        entityManager.getTransaction().commit();
        entityManager.close();
        logger.info("Record with id {} created", artifact.getId());
    }

    public void update(int id, Artifact updated) {
        logger.info("{}()", "update");
        var entityManager = Main.factory.createEntityManager();
        entityManager.getTransaction().begin();
        var artifactToUpdate = entityManager.find(Artifact.class, id);
        if (artifactToUpdate != null) {
            if (!updated.getGroupName().equals(artifactToUpdate.getGroupName())) {
                artifactToUpdate.setGroupName(updated.getGroupName());
            }
            if (!updated.getArtifactName().equals(artifactToUpdate.getArtifactName())) {
                artifactToUpdate.setArtifactName(updated.getArtifactName());
            }
            entityManager.getTransaction().commit();
            logger.info("Record with id {} updated", id);
        } else {
            entityManager.getTransaction().rollback();
            logger.info("Record with id {} not found", id);
        }
        entityManager.close();
    }

    public void delete(int id) {
        logger.info("{}()", "delete");
        var entityManager = Main.factory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.getReference(Artifact.class, id));
        entityManager.getTransaction().commit();
        entityManager.close();
        logger.info("Record with id {} deleted", id);
    }

    public void count() {
        logger.info("{}()", "count");
        var entityManager = Main.factory.createEntityManager();
        entityManager.getTransaction().begin();
        var result = entityManager.createQuery("select count(*) from Artifact a", Long.class).getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();
        logger.info("{} records found", result);
    }

}
