package controller;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.File_IO;
import dto.BoardDTO;

@Controller
public class WriteController {
	File_IO io;

	@RequestMapping(value = { "write.do", "modified.do" }, method = RequestMethod.GET)
	public ModelAndView process(HttpServletRequest request,
			@RequestParam(value = "index", required = false) String index) {
		//index 값 구하기
		String rootdir = request.getSession().getServletContext().getRealPath("/");
		String textdir = rootdir + "resources/datalist/";
		io = new File_IO();
		io.mkdir(request);
		
		ModelAndView mav = new ModelAndView();

		if (StringUtils.isEmpty(index) || index == "0") {
			int totalRecord = io.getResult(textdir,false);
			mav.addObject("index", totalRecord + 1);
			mav.setViewName("create");
		} else {
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			String uploadpath = rootpath + "resources/datalist"; 
			mav.addObject("file", io.readfile(uploadpath, Integer.parseInt(index)));
			mav.setViewName("modified");
		}

		return mav;
	}

	@RequestMapping(value = { "write.do", "modified.do" }, method = RequestMethod.POST)
	@ResponseBody
	public String writeProcess(HttpServletRequest request, BoardDTO bdto) {
		String flag = "false";

		if (io.createFile(request, bdto)) {
			flag = "true";
		}

		return flag;
	}

	@RequestMapping(value = "upload/images.do", method = RequestMethod.POST)
	@ResponseBody
	public String imageUpload(MultipartHttpServletRequest multi) {
		// 저장 경로 설정
		String root = multi.getSession().getServletContext().getRealPath("/");
		String path = root + "resources/images/";

		String newFileName = ""; // 업로드 되는 파일명
		
		UUID random = UUID.randomUUID();
		
		File dir = new File(path);
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		Iterator<String> files = multi.getFileNames();
		while (files.hasNext()) {
			String uploadFile = files.next();

			MultipartFile mFile = multi.getFile(uploadFile);
			String fileName = mFile.getOriginalFilename();
			
			newFileName = random + "_" + fileName;

			try {
				mFile.transferTo(new File(path + newFileName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return newFileName;
	}
}
