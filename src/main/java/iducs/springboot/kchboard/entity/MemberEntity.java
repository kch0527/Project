package iducs.springboot.kchboard.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member201812043")
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(length = 30, nullable = false)
    private String id;
    @Column(length = 30, nullable = false)
    private String pw;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 30)
    private String phone;
    @Column(length = 100)
    private String address;

    private Long block; //0: 조회가능  1:제한

    private String level;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    private List<BoardEntity> boardEntityList = new ArrayList<>();


}
