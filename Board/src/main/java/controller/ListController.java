package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import board.File_IO;
import board.Paging;
import dto.BoardDTO;

@Controller
public class ListController {
	private Paging paging;				// 페이징
	private int currentPage;			// 현재 페이지 
	
	@RequestMapping("list.do")
	public ModelAndView process(HttpServletRequest request, Paging pv) {
		String rootdir = request.getSession().getServletContext().getRealPath("/");
		String uploadDir = rootdir + "resources/datalist";
		
		ModelAndView mav = new ModelAndView();
		
		File_IO io = new File_IO();
		
		try {
			int totalRecord = io.getResult(uploadDir,true);
			if(totalRecord >= 1) {
				if(pv.getCurrentPage() == 0 ) { 		// 처음페이지는 1로 들어간다. 
					currentPage = 1; 					// 페이지를 1로 바꿔줌
				} else {
					currentPage = pv.getCurrentPage();
				}
				
				paging = new Paging(currentPage,totalRecord);
				
				List<JSONObject> alist = io.getlist(uploadDir);
				List<BoardDTO> blist = new ArrayList<BoardDTO>();
				List<BoardDTO> clist = new ArrayList<BoardDTO>();
				
				for(int i=0;i<alist.size();i++) {
					JSONObject obj = alist.get(i);
					BoardDTO bdto = new BoardDTO(Math.toIntExact((long) obj.get("index")), (String) obj.get("title"), 
												(String) obj.get("writer"), (String) obj.get("date"),
												(String) obj.get("contents"));
					blist.add(bdto);
				}
				
				blist.sort((board1, board2) -> {
					return board2.getIndex() - board1.getIndex();
				});
			
				for(int i=paging.getStartRow()-1; i<paging.getEndRow(); i++) {
					if(blist.size() <= i) break;
					else clist.add(blist.get(i));
				}
				
				mav.addObject("currentPage",currentPage);
				mav.addObject("list", clist);		
				mav.addObject("pv",paging);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		mav.setViewName("index");
		
		return mav;
	}
}
