package devholic22.board.repository.jdbcTemplate;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class JdbcTemplateBoardRepository implements BoardRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateBoardRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Board save(Board board) {
        String sql = "insert into board (writer, title, content, createdAt) values (:writer, :title, :content, :createdAt)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("writer", board.getWriter() != "anon" ? board.getWriter() : "anon")
                .addValue("title", board.getTitle())
                .addValue("content", board.getContent())
                .addValue("createdAt", board.getCreatedAt());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        int key = keyHolder.getKey().intValue();
        board.setId(key);
        return board;
    }

    @Override
    public Board findById(Integer id) {
        String sql = "select * from board where id = :id";

        try {
            Map<String, Object> param = Map.of("id", id);
            return template.queryForObject(sql, param, boardRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Board> findAll() {
        String sql = "select * from board";
        return template.query(sql, boardRowMapper());
    }

    @Override
    public List<Board> findByTitle(String title) {
        String sql = "select * from board where title like concat('%', :title, '%')";
        Map<String, Object> param = Map.of("title", title);
        return template.query(sql, param, boardRowMapper());
    }

    @Override
    public List<Board> findByCond(SearchCond searchCond) {
        String sql = "select * from board";

        String title = searchCond.getSearch() != null ? searchCond.getSearch() : "";
        int page = searchCond.getPage() != null ? searchCond.getPage() : 1;
        int EACH_PAGE = 3;
        int start = page > 0 ? (page - 1) * EACH_PAGE : 0;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("max", EACH_PAGE)
                .addValue("start", start);

        if (!title.equals("")) {
            sql += " where title like concat('%', :title, '%')";
        }

        sql += " limit :max offset :start";

        return template.query(sql, param, boardRowMapper());
    }

    @Override
    public void update(Integer id, BoardDto boardDto) {
        String sql = "update board set title=:title, content=:content where id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", boardDto.getTitle())
                .addValue("content", boardDto.getContent())
                .addValue("id", id);
        int count = template.update(sql, param);
        if (count != 1) {
            throw new NullPointerException();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from board where id=:id";
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        int count = template.update(sql, param);
        if (count != 1) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void clearStore() {
        // String sql = "truncate table boar";alter table board auto_increment=1";
        // SqlParameterSource param = new MapSqlParameterSource();
        // template.update(sql, param);
    }

    private RowMapper<Board> boardRowMapper() {
        return BeanPropertyRowMapper.newInstance(Board.class);
    }
}
