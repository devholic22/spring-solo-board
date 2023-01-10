package devholic22.board.config;

import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.MemoryBoardRepository;
import devholic22.board.service.BoardService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
        return new MemoryBoardRepository();
    }
}
