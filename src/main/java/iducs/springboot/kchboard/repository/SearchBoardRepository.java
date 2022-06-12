package iducs.springboot.kchboard.repository;

import iducs.springboot.kchboard.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {

    BoardEntity searchBoard();
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
