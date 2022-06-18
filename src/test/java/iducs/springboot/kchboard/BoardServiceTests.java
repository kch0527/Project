package iducs.springboot.kchboard;

import iducs.springboot.kchboard.domain.Board;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.domain.PageResultDTO;
import iducs.springboot.kchboard.repository.BoardRepository;
import iducs.springboot.kchboard.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    BoardService boardService;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void testRegister(){
        IntStream.rangeClosed(1, 50).forEach(i -> {
            Long seqLong = Long.valueOf(new Random().nextInt(50));
            seqLong = (seqLong == 0) ? 1: seqLong;
            Board dto = Board.builder()
                    .title("Test" + i)
                    .content("Content" + (50 - seqLong))
                    .writerSeq(seqLong)
                    .block(0L)
                    .views("0")
                    .build();
            Long bno = boardService.register(dto);
        });
    }

    @Test
    public void testRegisterOne(){
        Board board = Board.builder()
                .title("Title-")
                .content("Content-")
                .writerSeq(10L)
                .build();
        Long bno = boardService.register(board);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(3);
        PageResultDTO<Board, Object[]> result = boardService.getList(pageRequestDTO);
        for(Board dto : result.getDtoList())
            System.out.println("list = " + dto);
    }
}
