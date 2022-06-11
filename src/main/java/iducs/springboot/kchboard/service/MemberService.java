package iducs.springboot.kchboard.service;

import iducs.springboot.kchboard.domain.Member;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.domain.PageResultDTO;
import iducs.springboot.kchboard.entity.MemberEntity;

import java.util.List;

public interface MemberService {
    void create(Member member);
    Member readById(Long seq);
    List<Member> readAll();
    PageResultDTO<Member, MemberEntity> readListBy(PageRequestDTO pageRequestDTO);
    void update(Member member);
    void delete(Member member);

    Member readByName(Member member);
    Member readByEmail(Member member);

    Member loginByEmail(Member member);
}
