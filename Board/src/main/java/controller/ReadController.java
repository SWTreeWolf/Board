package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.File_IO;

@Controller
public class ReadController {
	@RequestMapping("read.do")
	public ModelAndView process(HttpServletRequest request,
			@RequestParam(value="index", required=true) String index) {
		File_IO io = new File_IO();
		
		String rootdir = request.getSession().getServletContext().getRealPath("/");
		String uploadDir = rootdir + "resources/datalist/";
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("file",io.readfile(uploadDir,Integer.parseInt(index)));
		mav.setViewName("read");
		return mav;
	}
}
