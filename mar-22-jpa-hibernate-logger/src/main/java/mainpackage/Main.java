package mainpackage;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {

    public static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("mainunit");

    public static void main(String[] args) {
        var repository = new ArtifactRepository();

        var ar = new Artifact();
        ar.setGroupName("jakarta.persistence");
        ar.setArtifactName("jakarta.persistence");
        repository.insert(ar);

        Artifact updated = new Artifact();
        updated.setGroupName("jakarta.persistence");
        updated.setArtifactName("jakarta.persistence-api");
        repository.update(4, updated);

        var artifacts = repository.selectAll();
        for (var artifact : artifacts) {
            System.out.println(artifact.getId() + " " + artifact.getGroupName() + " " + artifact.getArtifactName());
        }

        repository.delete(4);

        repository.count();
    }

}
