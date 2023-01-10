package devholic22.board;

import devholic22.board.config.MemoryConfig;
import devholic22.board.repository.BoardRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Import(MemoryConfig.class)
@SpringBootApplication(scanBasePackages = "devholic22.board.controller")
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestInit testInit(BoardRepository boardRepository) {
		return new TestInit(boardRepository);
	}
}
