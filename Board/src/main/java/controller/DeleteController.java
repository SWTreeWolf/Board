package controller;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import board.File_IO;
import dto.BoardDTO;

@Controller
public class DeleteController {
	@RequestMapping("delete.do")
	@ResponseBody
	//@RequestBody(required=true)String index
	public String deleteprocess(HttpServletRequest request, BoardDTO bdto) {
		String flag = "false";
		String rootdir = request.getSession().getServletContext().getRealPath("/");
		String textdir = rootdir + "resources/datalist";
		String imagedir = rootdir + "resources/images/";
		
		File_IO io = new File_IO();
		//index = bdto.getIndex().substring(index.lastIndexOf("=")+1);
	
		File upload_file = new File(textdir + "/" + bdto.getIndex() + ".json");
		
		if(upload_file.exists()) {
			JSONObject obj = io.readfile(textdir, bdto.getIndex());
			
			//수정사항
			flag = io.deleteFileProcess(request, bdto.getIndex());
			
			JSONArray arr = (JSONArray) obj.get("uploadfile");
			
			for(int i=0; i<arr.size();i++) {
				try {
					File upload_image = new File(imagedir + arr.get(i));
					if(upload_image.exists()) {
						upload_image.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			return flag;
		}else {
			return flag;
		}
		
	}
	
	@RequestMapping(value="upload/delete.do", method=RequestMethod.POST)
	public String deleteUpload(HttpServletRequest request, 
							  @RequestBody(required=true) String filename) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String path = root + "/resources/images/";
		filename = filename.substring(0,filename.indexOf("="));
		try {
			File file = new File(path + filename);
			if(file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
