package devholic22.board.config;

import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.jdbcTemplate.JdbcTemplateBoardRepository;
import devholic22.board.repository.memory.MemoryBoardRepository;
import devholic22.board.service.BoardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcTemplateConfig {

    private final DataSource dataSource;

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
        return new JdbcTemplateBoardRepository(dataSource);
    }
}
