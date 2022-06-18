package iducs.springboot.kchboard;


import iducs.springboot.kchboard.entity.MemberEntity;
import iducs.springboot.kchboard.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.stream.IntStream;


@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void testMemberInitial() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            MemberEntity memberEntity = MemberEntity.builder()
                    .id("id" + i)
                    .pw("pw" + i)
                    .name("name" + i)
                    .email("email" + i + "@induk.ac.kr")
                    .phone("phone" + new Random().nextInt(50))
                    .address("address" + new Random().nextInt(50))
                    .block(0L)
                    .level("1")
                    .build();
            memberRepository.save(memberEntity);
        });
    }

    @Test
    void testAdmin(){
        String str = "admin";
        MemberEntity entity = MemberEntity.builder()
                .id(str)
                .pw(str)
                .name("name-" + str)
                .email(str + "@induk.ac.kr")
                .phone("phone-" + new Random().nextInt(50))
                .address("address-" + new Random().nextInt(50))
                .block(0L)
                .level("3")
                .build();
        memberRepository.save(entity);
    }

    @Test
    void deleteMeber(){
        memberRepository.deleteById(50L);
    }




}
