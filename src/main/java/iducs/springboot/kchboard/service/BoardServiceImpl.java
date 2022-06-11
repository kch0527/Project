package iducs.springboot.kchboard.service;

import iducs.springboot.kchboard.domain.Board;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.domain.PageResultDTO;
import iducs.springboot.kchboard.entity.BoardEntity;
import iducs.springboot.kchboard.entity.MemberEntity;
import iducs.springboot.kchboard.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Long register(Board dto) {
        log.info(dto);
        dto.setBlock(0L);
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
       Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
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

}
