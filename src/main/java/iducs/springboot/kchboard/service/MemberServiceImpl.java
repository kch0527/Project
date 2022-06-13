package iducs.springboot.kchboard.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import iducs.springboot.kchboard.domain.Member;
import iducs.springboot.kchboard.domain.PageRequestDTO;
import iducs.springboot.kchboard.domain.PageResultDTO;
import iducs.springboot.kchboard.entity.MemberEntity;
import iducs.springboot.kchboard.entity.QMemberEntity;
import iducs.springboot.kchboard.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void create(Member member) {
        MemberEntity entity = dtoToEntity(member);
        entity.setBlock(0L);
        memberRepository.save(entity);
    }

    private MemberEntity dtoToEntity(Member member){
        MemberEntity entity = MemberEntity.builder()
                .seq(member.getSeq())
                .id(member.getId())
                .pw(member.getPw())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .address(member.getAddress())
                .block(member.getBlock())
                .build();
        return entity;
    }

    @Override
    public Member readById(Long seq) {
        Member member = null;
        Optional<MemberEntity> result = memberRepository.findById(seq);
        if (result.isPresent()){
            member = entityToDto(result.get());
        }
        return member;
    }

    private Member entityToDto(MemberEntity entity){
        Member member = Member.builder()
                .seq(entity.getSeq())
                .id(entity.getId())
                .pw(entity.getPw())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address((entity.getAddress()))
                .block(entity.getBlock())
                .build();
        return member;
    }

    @Override
    public PageResultDTO<Member, MemberEntity> readListBy(PageRequestDTO pageRequestDTO){
        Sort sort = Sort.by("seq").descending();
        if (pageRequestDTO.getSort() == null)
            sort = Sort.by("seq").descending();
        else if(pageRequestDTO.getSort().equals("asc"))
            sort = Sort.by("seq").ascending();
        Pageable pageable = pageRequestDTO.getPageable(sort);
        BooleanBuilder booleanBuilder = findByCondition(pageRequestDTO);
        Page<MemberEntity> result = memberRepository.findAll(booleanBuilder, pageable);
        //Page<MemberEntity> result = memberRepository.findAll(pageable);
        Function<MemberEntity, Member> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder findByCondition(PageRequestDTO pageRequestDTO){
        String type = pageRequestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMemberEntity qMemberEntity = QMemberEntity.memberEntity;
        BooleanExpression expression = qMemberEntity.seq.gt(0L); //where seq > 0 and title == "ti"
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("e")) //email 검색
            conditionBuilder.or(qMemberEntity.email.contains(keyword));
        if (type.contains("p")) //phone 검색
            conditionBuilder.or(qMemberEntity.phone.contains(keyword));
        if (type.contains("a")) //address 검색
            conditionBuilder.or(qMemberEntity.address.contains(keyword));
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

    @Override
    public List<Member> readAll() {
        List<Member> members = new ArrayList<>();
        List<MemberEntity> entities = memberRepository.findAll();
        for (MemberEntity entity: entities) {
            Member member = entityToDto(entity);
            members.add(member);
        }
        return members;
    }

    @Override
    public void update(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.save(entity);
    }

    @Override
    public void delete(Member member) {
        MemberEntity entity = dtoToEntity(member);
        memberRepository.deleteById(entity.getSeq());
    }

    @Override
    public Member readByName(Member member) {
        return null;
    }

    @Override
    public Member readByEmail(Member member) {
        return null;
    }

    @Override
    public Member loginByEmail(Member member){
        Member dto = null;
        Object result = memberRepository.getMemberByEmail(member.getEmail(), member.getPw());
        if (result != null){
            dto = entityToDto((MemberEntity) result);
        }
        return dto;
    }
}
