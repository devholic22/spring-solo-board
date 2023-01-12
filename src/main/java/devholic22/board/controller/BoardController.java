package devholic22.board.controller;

import devholic22.board.domain.Board;
import devholic22.board.repository.dto.BoardDto;
import devholic22.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 등록 폼 조회
    @GetMapping
    // 주의: @GetMapping("/")과 같이 입력하면 제대로 인식하지 못함
    public String getUpload() {
        return "addForm";
    }

    // 등록 요청
    // @ModelAttribute는 객체를 model에 집어넣는 것 까지 해준다.
    @PostMapping
    public String postUpload(@ModelAttribute Board board) {
        // 알게 된 점: 꼭 모든 요소가 ModelAttribute 사용 때 들어 있을 필요는 없는 듯 하다.
        // ex: 저장할 때 쓰이는 요소는 title과 content인데, title과 content "만" 들어 있는 BoardDTO가 아니라 Board로 가져와도 문제는 없다.
        // 그러나 서비스에서 저장할 때 board.setId와 같이 DTO에는 없는 요소를 세팅하므로 여기에서는 DTO가 아니라 Board로 가져왔다.
        boardService.write(board);
        return "redirect:/";
    }

    // 특정 게시물 조회
    @GetMapping("/{id}")
    public String OneBoard(@PathVariable String id, Model model) {
        Board board = boardService.findOne(Long.parseLong(id));
        model.addAttribute("board", board);
        return "board";
    }

    // 특정 게시물 수정 페이지
    @GetMapping("/{id}/edit")
    public String EditBoard(@PathVariable String id, Model model) {
        Board board = boardService.findOne(Long.parseLong(id));
        model.addAttribute("board", board);
        return "editForm";
    }

    // 특정 게시물 수정 요청
    // PutMapping이 통하지 않는다.. 조치 완료!
    @PutMapping("/{id}")
    public String EditBoard(@PathVariable String id, @ModelAttribute BoardDto boardDto) {
        boardService.fix(Long.valueOf(id), boardDto);
        return "redirect:/";
    }

    // 특정 게시물 삭제 요청
    @DeleteMapping("/{id}")
    public String DeleteBoard(@PathVariable String id) {
        boardService.remove(Long.valueOf(id));
        return "redirect:/";
    }
}
