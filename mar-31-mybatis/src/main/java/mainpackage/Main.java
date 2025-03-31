package mainpackage;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            DriverManager.getConnection(
                    "jdbc:h2:mem:database01;INIT=runscript from 'classpath:create.sql'",
                    "username",
                    "password"
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        InputStream inputStream;

        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            Artifact h2 = new Artifact();
            h2.setGroupName("h2group");
            h2.setArtifactName("h2database");

            ArtifactMapper artifactMapper = session.getMapper(ArtifactMapper.class);
            artifactMapper.insert(h2);
            session.commit();
        }

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ArtifactMapper artifactMapper = session.getMapper(ArtifactMapper.class);
            List<Artifact> artifacts = artifactMapper.selectAll();
            for (var el : artifacts) {
                System.out.println(el.getId() + " " + el.getGroupName() + " " + el.getArtifactName());
            }
        }
    }



}
