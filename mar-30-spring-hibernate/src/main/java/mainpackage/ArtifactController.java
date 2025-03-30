package mainpackage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {

    private final ArtifactRepository artifactRepository;

    public ArtifactController(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    @GetMapping
    public ResponseEntity<List<Artifact>> selectAll() {
        return new ResponseEntity<>(artifactRepository.selectAll(), HttpStatus.OK);
    }

}
