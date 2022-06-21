package iducs.springboot.kchboard.controller;

import iducs.springboot.kchboard.domain.AlbumRequestDto;
import iducs.springboot.kchboard.domain.Member;
import iducs.springboot.kchboard.entity.Album;
import iducs.springboot.kchboard.entity.Comment;
import iducs.springboot.kchboard.entity.MemberEntity;
import iducs.springboot.kchboard.service.AlbumService;
import iducs.springboot.kchboard.service.CommentService;
import iducs.springboot.kchboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class AlbumController {
	
	private final AlbumService albumService;
	private final CommentService commentService;
	private final MemberService memberService;
	
	@GetMapping("/album/list")
	public String getAlbumList(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "5") Integer size) throws Exception {
		
		try {
			model.addAttribute("resultMap", albumService.findAll(page, size));
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/album/list";
	}
	
	@GetMapping("/album/add")
	public String getAlbumAdd(Model model, AlbumRequestDto albumRequestDto) {
		return "album/albumRegform";
	}
	
	@GetMapping("/album/view/{id}")
	public String getAlbumViewPage(@PathVariable Long id, Model model, AlbumRequestDto albumRequestDto) throws Exception {
		
		try {
			if (albumRequestDto.getId() != null) {
				model.addAttribute("resultMap", albumService.findById(albumRequestDto.getId()));
				model.addAttribute("album", albumService.findByAlbum(id));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/album/view";
	}
	
	@PostMapping("/album/add/action")
	public String AlbumAddAction(Model model, AlbumRequestDto albumRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		try {
			if (!albumService.save(albumRequestDto, multiRequest)) {
				throw new Exception("#Exception albumAddAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/album/list";
	}
	

	@PostMapping("/album/view/delete")
	public String albumViewDeleteAction(Model model, @RequestParam() Long id) throws Exception {
		
		try {
			albumService.deleteById(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/album/list";
	}

	@PostMapping("/album/view/{id}")
	public String getAlbumAddComment(@PathVariable Long id, Model model, @ModelAttribute Comment comment, HttpSession session, RedirectAttributes redirectAttributes){
		try {
			Album album = albumService.findByAlbum(id);
			MemberEntity entity = memberService.commentByMember((Member) session.getAttribute("login"));

			comment.setAlbum(album);
			comment.setMember(entity);
			comment.setNowTime(commentService.nowTime());
			Comment saveComment = commentService.addComment(comment);
			redirectAttributes.addAttribute("commentId", saveComment.getId());
			redirectAttributes.addAttribute("status", true);

			return "redirect:/album/view/" + id;
		} catch (Exception e) {
			e.printStackTrace();
			return "album/error";
		}
	}


}
