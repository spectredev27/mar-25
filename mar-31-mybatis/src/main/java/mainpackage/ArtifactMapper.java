package mainpackage;

import java.util.List;

public interface ArtifactMapper {

    List<Artifact> selectAll();
    void insert(Artifact artifact);

}
