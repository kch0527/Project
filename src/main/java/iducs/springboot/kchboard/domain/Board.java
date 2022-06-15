package iducs.springboot.kchboard.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private Long bno;
    private String title;
    private String content;
    private Long writerSeq;
    private String writerId;
    private String writerName;
    private String writerEmail;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    private Long block;


    private int replyCount;
}
