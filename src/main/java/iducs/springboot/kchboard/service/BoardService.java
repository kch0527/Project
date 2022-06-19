package iducs.springboot.kchboard.service;

import iducs.springboot.kchboard.domain.Board;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.domain.PageResultDTO;
import iducs.springboot.kchboard.entity.BoardEntity;
import iducs.springboot.kchboard.entity.MemberEntity;

public interface BoardService {
    Long register(Board dto);

    PageResultDTO<Board, Object[]> getList(PageRequestDTO pageRequestDTO);
    Board getById(Long bno);
    Long modify(Board dto);
    void update(Board board);
    void delete(Board board);
    void saveView(Long bno);


    default BoardEntity dtoToEntity(Board dto){
        MemberEntity member = MemberEntity.builder()
                .seq(dto.getWriterSeq())
                .build();
        BoardEntity board = BoardEntity.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .block(dto.getBlock())
                .views(dto.getViews())
                .category(dto.getCategory())
                .build();
        return board;
    }

    default Board entityToDto(BoardEntity entity, MemberEntity member, Long replyCount){
        Board dto = Board.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writerSeq(member.getSeq())
                .writerId(member.getId())
                .writerName(member.getName())
                .writerEmail(member.getEmail())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .replyCount(replyCount.intValue())
                .block(entity.getBlock())
                .views(entity.getViews())
                .category(entity.getCategory())
                .build();
        return dto;
    }
}
