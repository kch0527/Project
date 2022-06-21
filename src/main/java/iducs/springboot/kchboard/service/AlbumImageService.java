package iducs.springboot.kchboard.service;


import iducs.springboot.kchboard.domain.AlbumImageResponseDto;
import iducs.springboot.kchboard.entity.AlbumImage;
import iducs.springboot.kchboard.repository.AlbumImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.security.SecureRandom;
import java.util.*;
import java.util.Map.Entry;

@RequiredArgsConstructor
@Service
public class AlbumImageService {
	
	private final AlbumImageRepository albumImageRepository;
	
	public AlbumImageResponseDto findById(Long id) throws Exception {
		return new AlbumImageResponseDto(albumImageRepository.findById(id).get());
	}
	
	public List<Long> findByAlbumId(Long albumId) throws Exception {
		return albumImageRepository.findByAlbumId(albumId);
	}
	
	public boolean uploadFile(MultipartHttpServletRequest multiRequest, Long albumId) throws Exception {
		
		if (albumId == null) throw new NullPointerException("Empty BOARD_ID.");

		Map<String, MultipartFile> files = multiRequest.getFileMap();

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		
		MultipartFile mFile;
		
		String savaFilePath = "", randomFileName = "";
		
		Calendar cal = Calendar.getInstance();

		List<Long> resultList = new ArrayList<Long>();
		
		while (itr.hasNext()) {
		
			Entry<String, MultipartFile> entry = itr.next();
	
			mFile = entry.getValue();
			
			int fileSize = (int) mFile.getSize();
			
			if (fileSize > 0) {
				
				String filePath = "C:\\chan\\images\\";

				filePath = filePath + File.separator + String.valueOf(cal.get(Calendar.YEAR)) + File.separator + String.valueOf(cal.get(Calendar.MONTH) + 1);
				randomFileName = "FILE_" + RandomStringUtils.random(8, 0, 0, false, true, null, new SecureRandom());
				
				String realFileName = mFile.getOriginalFilename();
				String fileExt = realFileName.substring(realFileName.lastIndexOf(".") + 1);
				String saveFileName = randomFileName + "." + fileExt;
				String saveFilePath = filePath + File.separator + saveFileName;
				
				File filePyhFolder = new File(filePath);
				
				if (!filePyhFolder.exists()) {
					if (!filePyhFolder.mkdirs()) {
						throw new Exception("File.mkdir() : Fail."); 
					}
				}
				
				File saveFile = new File(saveFilePath);

				if (saveFile.isFile()) {
					boolean _exist = true;
					
					int index = 0;

					while (_exist) {
						index++;
						
						saveFileName = randomFileName + "(" + index + ")." + fileExt;
						
						String dictFile = filePath + File.separator + saveFileName;
						
						_exist = new File(dictFile).isFile();
						
						if (!_exist) {
							savaFilePath = dictFile;
						}
					}
					
					mFile.transferTo(new File(savaFilePath));
				} else {
					mFile.transferTo(saveFile);
				}
				
				AlbumImage albumImage = AlbumImage.builder()
						.albumId(albumId)
						.origFileName(realFileName)
						.saveFileName(saveFileName)
						.fileSize(fileSize)
						.fileExt(fileExt)
						.filePath(filePath)
						.deleteYn("N")
						.build();
				
				resultList.add(albumImageRepository.save(albumImage).getId());
			}
		}
		
		return (files.size() == resultList.size()) ? true : false;
	}
	
	public int updateDeleteYn(Long[] deleteIdList) throws Exception {
		return albumImageRepository.updateDeleteYn(deleteIdList);
	}
	
	public int deleteAlbumFileYn(Long[] boardIdList) throws Exception {
		return albumImageRepository.deleteAlbumFileYn(boardIdList);
	}
}