package devholic22.board.repository.jpa;

import devholic22.board.domain.Board;
import devholic22.board.repository.BoardRepository;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.repository.dto.SearchCond;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@Transactional // JPA의 모든 데이터 변경은 트랜잭션 안에서 일어난다
// 트랜잭션을 적용하지 않을 경우 No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call가 일어난다
public class JpaBoardRepository implements BoardRepository {
    private final EntityManager em;

    public JpaBoardRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Board save(Board board) {
        board.setWriter("anon");
        em.persist(board);
        return board;
    }

    @Override
    public Board findById(Integer id) {
        return em.find(Board.class, id);
    }

    @Override
    public List<Board> findAll() {
        String jpql = "select i from Board i";
        return em.createQuery(jpql, Board.class)
                .getResultList();
    }

    @Override
    public List<Board> findByTitle(String title) {
        String jpql = "select i from Board i where i.title like concat('%', :search, '%')";
        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        query.setParameter("search", title);
        return query.getResultList();
    }

    @Override
    public List<Board> findByCond(SearchCond searchCond) {
         String jpql = "select i from Board i";
         int page = searchCond.getPage() != null ? searchCond.getPage() : 1;
         String search = searchCond.getSearch() != null ? searchCond.getSearch() : "";
         List<String> param = new ArrayList<>();

        int EACH_PAGE = 3;
        int start = page > 0 ? ((page - 1) * EACH_PAGE) : 0;

         if (!search.equals("")) {
             jpql += " where i.title like concat('%', :search, '%')";
             param.add(search);
         }

        TypedQuery<Board> query = em.createQuery(jpql, Board.class);
        if (!search.equals("")) {
            query.setParameter("search", search);
        }
        query.setFirstResult(start);
        query.setMaxResults(EACH_PAGE);
        return query.getResultList();
    }

    @Override
    public void update(Integer id, BoardDto boardDto) {
        Board findBoard = em.find(Board.class, id);
        if (findBoard == null) {
            throw new NullPointerException();
        }
        findBoard.setTitle(boardDto.getTitle());
        findBoard.setContent(boardDto.getContent());
    }

    @Override
    public void delete(Integer id){
        Board findBoard = em.find(Board.class, id);
        if (findBoard == null) {
            throw new NoSuchElementException();
        }
        em.remove(findBoard);
    }

    @Override
    public void clearStore() {

    }
}
