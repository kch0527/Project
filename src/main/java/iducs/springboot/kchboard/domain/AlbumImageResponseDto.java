package iducs.springboot.kchboard.domain;

import iducs.springboot.kchboard.entity.AlbumImage;
import lombok.Getter;

@Getter
public class AlbumImageResponseDto {
	
	private String origFileName;
	private String saveFileName;
	private String filePath;
	
	public AlbumImageResponseDto(AlbumImage entity) {
		this.origFileName = entity.getOrigFileName();
		this.saveFileName = entity.getSaveFileName();
		this.filePath = entity.getFilePath();
	}

	@Override
	public String toString() {
		return "FileMstResponseDto [origFileName=" + origFileName + ", saveFileName=" + saveFileName + ", filePath="
				+ filePath + "]";
	}
}