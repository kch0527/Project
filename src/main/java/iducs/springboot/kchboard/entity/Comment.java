package iducs.springboot.kchboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Table(name="comment201812043")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    private String text;

    private String nowTime;

}
