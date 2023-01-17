package devholic22.board;

import devholic22.board.config.JdbcTemplateConfig;
import devholic22.board.config.MemoryConfig;
import devholic22.board.config.MyBatisConfig;
import devholic22.board.repository.BoardRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.sql.DataSource;

// @Import(MemoryConfig.class)
// @Import(JdbcTemplateConfig.class)
@Import(MyBatisConfig.class)
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

	/*
	@Bean
	@Profile("test")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/board");
		dataSource.setUsername("sa");
		return dataSource;
	}
	*/

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}
}
