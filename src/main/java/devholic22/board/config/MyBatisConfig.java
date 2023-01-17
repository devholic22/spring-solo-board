package devholic22.board.config;

import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.mybatis.BoardMapper;
import devholic22.board.repository.mybatis.MyBatisBoardRepository;
import devholic22.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MyBatisConfig {
    private final BoardMapper boardMapper;


    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
        System.out.println("boardMapper" + boardMapper);
        return new MyBatisBoardRepository(boardMapper);
    }
}
