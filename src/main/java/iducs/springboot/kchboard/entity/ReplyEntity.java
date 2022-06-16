package iducs.springboot.kchboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_reply201812043")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class ReplyEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;
    private String replier;

    @ManyToOne
    private BoardEntity board;
}
