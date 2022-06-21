package iducs.springboot.kchboard.service;


import iducs.springboot.kchboard.domain.AlbumRequestDto;
import iducs.springboot.kchboard.domain.AlbumResponseDto;
import iducs.springboot.kchboard.entity.Album;
import iducs.springboot.kchboard.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlbumService {

	private final AlbumRepository albumRepository;
	private final AlbumImageService albumImageService;
	
	@Transactional
	public boolean save(AlbumRequestDto albumRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		Album result = albumRepository.save(albumRequestDto.toEntity());
		
		boolean resultFlag = false;
		
		if (result != null) {
			albumImageService.uploadFile(multiRequest, result.getId());
			resultFlag = true;
		}
		
		return resultFlag;
	}

	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		Page<Album> list = albumRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerTime")));
		
		resultMap.put("list", list.stream().map(AlbumResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}
	
	public HashMap<String, Object> findById(Long id) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
		
		albumRepository.updateAlbumReadCntInc(id);
		
		AlbumResponseDto info = new AlbumResponseDto(albumRepository.findById(id).get());
		
		resultMap.put("info", info);
		resultMap.put("fileList", albumImageService.findByAlbumId(info.getId()));
		
		return resultMap;
	}
	

	
	public void deleteById(Long id) throws Exception {
		Long[] idArr = {id};
		albumImageService.deleteAlbumFileYn(idArr);
		albumRepository.deleteById(id);
	}

	public Album findByAlbum(Long id){
		return albumRepository.findById(id).get();
	}
	

}
