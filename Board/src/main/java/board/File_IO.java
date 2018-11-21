package board;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dto.BoardDTO;

public class File_IO {
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public void mkdir(HttpServletRequest request) {
		String rootdir = request.getSession().getServletContext().getRealPath("/");
		String textdir = rootdir + "resources/datalist";
		String imagedir = rootdir + "resources/images";

		File file = new File(textdir);
		File file2 = new File(imagedir);

		if (!file.exists() || !file2.exists()) {
			file.mkdirs();
			file2.mkdirs();
		}
	}

	// 수정사항
	public String deleteFileProcess(HttpServletRequest request, int index) {
		String rootdir = request.getSession().getServletContext().getRealPath("/");
		String textdir = rootdir + "resources/datalist/";
		
		JSONObject obj = readfile(textdir, index);
		
		File filelist = new File(textdir);

		if (!filelist.exists()) {
			filelist.mkdirs();
			System.out.println(filelist + " 디렉토리를 생성");
		}

		try {
			String filename = filelist + File.separator + String.valueOf(index) + ".json";
			File file = new File(filename);
			FileWriter fw = new FileWriter(file, false);

			JSONObject boardInfo = new JSONObject();

			boardInfo.put("index", obj.get("index"));
			boardInfo.put("title", obj.get("title"));
			boardInfo.put("writer", obj.get("writer"));
			boardInfo.put("date", obj.get("date"));
			boardInfo.put("contents", obj.get("contents"));
			boardInfo.put("exists", "false");

			fw.write(boardInfo.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
		return "true";
	}

	public boolean createFile(HttpServletRequest request, BoardDTO bdto) {
		String rootdir = request.getSession().getServletContext().getRealPath("/");
		String textdir = rootdir + "resources/datalist/";

		File filelist = new File(textdir);

		if (!filelist.exists()) {
			filelist.mkdirs();
			System.out.println(filelist + " 디렉토리를 생성");
		}

		try {
			String filename = filelist + File.separator + bdto.getIndex() + ".json";
			File file = new File(filename);
			FileWriter fw = new FileWriter(file, false);

			JSONObject boardInfo = new JSONObject();

			boardInfo.put("index", bdto.getIndex());
			boardInfo.put("title", bdto.getTitle());
			boardInfo.put("writer", bdto.getWriter());
			boardInfo.put("date", bdto.getDate());
			boardInfo.put("contents", bdto.getContents());

			JSONArray uploadarr = new JSONArray();
			String temp[] = bdto.getContents().split("src=\"|\"");
			for (String s : temp) {
				if (s.contains("http://localhost:8090/myresource/resources/images/")) {
					s = s.substring(s.lastIndexOf("/") + 1);
					uploadarr.add(s);
				}
			}
			boardInfo.put("uploadfile", uploadarr);
			boardInfo.put("exists", "true");

			fw.write(boardInfo.toJSONString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//flag : true일때 보여줘야 될 목록, false 실제 가지고 있는 파일
	public int getResult(String dir, boolean flag) {
		int res = 0;
		int total = 0;
		File filelist = new File(dir);

		if (!filelist.exists()) {
			System.out.println(filelist.getAbsolutePath() + "폴더가 존재하지 않습니다!");
			return -1;
		}

		File[] list = filelist.listFiles();

		for (File file : list) {
			if (file.isFile()) {
				try {
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
					JSONObject jsonObject = (JSONObject) obj;
					if(flag == true && jsonObject.get("exists").equals("true")) {
						res++;
						total++;
					}else {
						total++;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(flag == true) {
			return res;
		}else {
			return total;
		}
	}

	public JSONObject readfile(String dir, int number_index) {
		JSONObject jsonObject = null;
		String index = String.valueOf(number_index);
		try {
			JSONParser parser = new JSONParser();

			File file = new File(dir + "/" + index + ".json");
			Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
			jsonObject = (JSONObject) obj;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;
	}
	
	public List<JSONObject> getlist(String dir) {
		List<JSONObject> aList = new ArrayList<JSONObject>();

		File filelist = new File(dir);
		
		File[] list = filelist.listFiles();
		
		for(File file : list) {
			try {
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(new FileReader(file.getAbsolutePath()));
				JSONObject jsonObject = (JSONObject) obj;
				if(jsonObject.get("exists").equals("true")) {
					aList.add(jsonObject);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return aList;
	}
}
