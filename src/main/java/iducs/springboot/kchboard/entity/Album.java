package iducs.springboot.kchboard.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "album201812043")
@Getter
@Entity
public class Album extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int views;

	@Builder
	public Album(Long id, String title, int views, MemberEntity member) {
		this.id = id; 
		this.title = title;
		this.views = views;
		this.member = member;
	}

	@ManyToOne
	private	MemberEntity member;

	@OneToMany(mappedBy = "album", cascade=CascadeType.REMOVE)
	private List<Comment> commentList = new ArrayList<>();
}
