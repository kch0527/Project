package iducs.springboot.kchboard.controller;

import iducs.springboot.kchboard.domain.AlbumImageRequestDto;
import iducs.springboot.kchboard.domain.AlbumImageResponseDto;
import iducs.springboot.kchboard.service.AlbumImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Controller
public class AlbumImageController {
	
	private final AlbumImageService albumImageService;
	
	@GetMapping("/file/download")
	public void downloadFile(@RequestParam() Long id, HttpServletResponse response) throws Exception {
		try {
			AlbumImageResponseDto fileInfo = albumImageService.findById(id);
			if (fileInfo == null) throw new FileNotFoundException("Empty FileData.");
			File dFile  = new File(fileInfo.getFilePath(), fileInfo.getSaveFileName());

			int fSize = (int) dFile.length();

			if (fSize > 0) {
				String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileInfo.getOrigFileName(), "UTF-8");
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-Disposition", encodedFilename);
				response.setContentLengthLong(fSize);

				BufferedInputStream in = null;
				BufferedOutputStream out = null;

				in = new BufferedInputStream(new FileInputStream(dFile));

				out = new BufferedOutputStream(response.getOutputStream());
				
				try {
					byte[] buffer = new byte[4096];
				 	int bytesRead=0;

				 	while ((bytesRead = in.read(buffer))!=-1) {
						out.write(buffer, 0, bytesRead);
					}
				 	out.flush();
				}
				finally {
					in.close();
					out.close();
				}
		    } else {
		    	throw new FileNotFoundException("Empty FileData.");
		    }
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@PostMapping("/file/delete.ajax")
	public String updateDeleteYn(Model model, AlbumImageRequestDto albumImageRequestDto) throws Exception {
		try {
			model.addAttribute("result", albumImageService.updateDeleteYn(albumImageRequestDto.getIdArr()));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "jsonView";
	}
}