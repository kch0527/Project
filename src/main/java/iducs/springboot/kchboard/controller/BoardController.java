package iducs.springboot.kchboard.controller;

import iducs.springboot.kchboard.domain.Board;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("")
    public String getList(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("list", boardService.getList(pageRequestDTO));
        return "boards/list";
    }

    @GetMapping("/{bno}")
    public String get(@PathVariable Long bno, Model model){
        if (boardService.getById(bno).getBlock() == 0L) {
            model.addAttribute("dto", boardService.getById(bno));
            return "boards/read";
        }
        model.addAttribute(bno);
        return "boards/limit";
    }

    @GetMapping("/regform")
    public String getRegForm(Model model){
        model.addAttribute("dto", Board.builder().build());
        return "boards/regform";
    }

    @PostMapping("")
    public String post(@ModelAttribute("dto") Board dto){
        /*
        Long seqLong = Long .valueOf(new Random().nextInt(50));
        seqLong = (seqLong == 0)? 1L: seqLong;
        dto.setWriterSeq(seqLong);
         */
        Long bno = boardService.register(dto);
        return "redirect:/boards/" +bno;
    }

    @GetMapping("/{bno}/upform")
    public String getUpForm(@PathVariable("bno") Long bno, Model model){
        model.addAttribute("board", boardService.getById(bno));
        return "/boards/upForm";
    }

    @PutMapping("/{bno}")
    public String putBoard(@PathVariable("bno") Long bno, @ModelAttribute("dto") Board board, Model model){
        board.setWriterName(boardService.getById(bno).getWriterName());
        board.setModDate(boardService.getById(bno).getModDate());
        board.setWriterEmail(boardService.getById(bno).getWriterEmail());
        board.setBlock(boardService.getById(bno).getBlock());
        boardService.update(board);
        model.addAttribute(board);
        return "/boards/read";
    }

    @DeleteMapping("/{bno}")
    public String deleteMember(@PathVariable Long bno){
        boardService.delete(boardService.getById(bno));
        return "redirect:/boards";
    }

    @PostMapping("/{bno}")
    public String boardBlock(@PathVariable Long bno){
        Board board = boardService.getById(bno);
        if (board.getBlock() == 0L){
            board.setBlock(1L);
            boardService.update(board);
        }
        else{
            board.setBlock(0L);
            boardService.update(board);
        }
        return "boards/read";
    }
}
