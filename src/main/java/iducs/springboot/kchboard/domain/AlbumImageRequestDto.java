package iducs.springboot.kchboard.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlbumImageRequestDto {
	private Long id;
	private Long[] idArr;
	private String fileId;
}