package iducs.springboot.kchboard.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "t_board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "writer")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;
    private String content;

    private String views;

    private Long block; //0: 조회가능  1:제한
    @ManyToOne
    private MemberEntity writer;
}

