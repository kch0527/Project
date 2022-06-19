package iducs.springboot.kchboard.service;

import iducs.springboot.kchboard.domain.Board;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.domain.PageResultDTO;
import iducs.springboot.kchboard.entity.BoardEntity;
import iducs.springboot.kchboard.entity.MemberEntity;
import iducs.springboot.kchboard.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Function;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Long register(Board dto){
        log.info(dto);
        dto.setBlock(0L);
        dto.setViews(0L);
        BoardEntity boardEntity = dtoToEntity(dto);
        boardRepository.save(boardEntity);
        return boardEntity.getBno();
    }

    @Override
    public PageResultDTO<Board, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(">>>>" + pageRequestDTO);
        Function<Object[], Board> fn =
                (entity -> entityToDto((BoardEntity)entity[0],
                        (MemberEntity) entity[1], (Long) entity[2]));
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();
        String category = pageRequestDTO.getCategory();
        Pageable pageable = null;
        String page = pageRequestDTO.getSort();
        String asc = "asc";
        pageable = pageRequestDTO.getPageable(Sort.by("views").descending());
        if(page != null) {
            if(page.equals(asc)) {
                pageable = pageRequestDTO.getPageable(Sort.by("views").ascending());
            }
        }

        Page<Object[]> result = boardRepository.searchPage(type, keyword, pageable, category);

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public Board getById(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);
        Object[] en = (Object[]) result;
        return entityToDto((BoardEntity) en[0], (MemberEntity) en[1], (Long) en[2]);
    }

    @Override
    public Long modify(Board dto) {
        return null;
    }


    @Override
    public void update(Board board) {
        BoardEntity entity = dtoToEntity(board);
        boardRepository.save(entity);
    }

    @Override
    public void delete(Board board) {
        BoardEntity entity = dtoToEntity(board);
        boardRepository.deleteById(entity.getBno());
    }

    @Override
    @Transactional
    public void saveView(Long bno){
        Board board = getById(bno);
        long view = board.getViews() + 1;
        board.setViews(view);
        update(board);
    }




}
