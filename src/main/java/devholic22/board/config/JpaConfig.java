package devholic22.board.config;

import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.jpa.JpaBoardRepository;
import devholic22.board.service.BoardService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    private final EntityManager em;

    public JpaConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
        return new JpaBoardRepository(em);
    }
}
