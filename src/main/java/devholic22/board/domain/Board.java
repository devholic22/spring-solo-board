package devholic22.board.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
// @Table(name = "Board") 클래스 이름과 테이블 이름 같으면 생략 가능
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20) // 이름이 같거나 별도의 설정을 하지 않을 거면 생략 가능
    // length 설정은 JPA가 SQL 테이블을 생성할 때 사용됨
    private String writer;

    private String title;
    private String content;

    @Column(name = "createdat")
    private String createdAt;

    public Board() { // JPA는 public or protected 기본 생성자가 필수이다.
        this.createdAt = timeFormat();
    }

    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = timeFormat();
    }

    public String timeFormat() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
