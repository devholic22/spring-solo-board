package devholic22.board.repository.mybatis;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    @Insert("insert into board (writer, title, content, createdAt) values (#{writer}, #{title}, #{content}, #{createdAt})")
    void save(Board board);

    @Update("update board set title=#{boardDto.title}, content=#{boardDto.content} where id=#{id}")
    void update(@Param("id") Integer id, @Param("boardDto") BoardDto boardDto);

    @Select("select * from board where id=#{id}")
    Board findById(@Param("id") Integer id);

    @Select("select * from board")
    List<Board> findAll();

    List<Board> findByTitle(String title);

    List<Board> findByCond(SearchCond searchCond);

    @Delete("delete from board where id=#{id}")
    void delete(Integer id);
}
