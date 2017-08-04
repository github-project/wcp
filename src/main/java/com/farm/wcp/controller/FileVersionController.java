/**  
 * @Title: FileVersionController.java 
 * @Package com.farm.wcp.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author <作者姓名>  
 * @date 2017-7-6下午6:57:24
*/
package com.farm.wcp.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.farm.core.page.ViewMode;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.domain.FarmDoctext;
import com.farm.doc.domain.ex.DocEntire;
import com.farm.doc.exception.DocNoExistException;
import com.farm.doc.server.FarmDocManagerInter;
import com.farm.doc.server.FarmFileIndexManagerInter;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.wcp.util.ThemesUtil;

@RequestMapping("/filedoc")
@Controller
public class FileVersionController {
	private final static Logger log = Logger.getLogger(FileVersionController.class);
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	@Resource
	private FarmFileIndexManagerInter farmFileIndexManagerImpl;
	@Resource
	private FarmDocManagerInter farmDocManagerImpl;
	/**
	 * 查看附件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/view/PubFile{fileid}", method = RequestMethod.GET)
	public ModelAndView showFile(@PathVariable("fileid") String fileid,HttpSession session, HttpServletRequest request)
			throws Exception {
		ViewMode page = ViewMode.getInstance();
		try {
			FarmDocfile file = farmFileManagerImpl.getFile(fileid);
			if (file == null) {
				throw new DocNoExistException();
			}
			file.setUrl(farmFileManagerImpl.getFileURL(file.getId()));
			page.putAttr("file", file);
			//获取附件list
			String fileName = file.getFilename();
			String queryNameStr =null;
			List<FarmDocfile> fileLists = new ArrayList<FarmDocfile>();
			if(fileName.contains("_")){
				queryNameStr = fileName.substring(0, fileName.lastIndexOf("_"));
			}else{
				queryNameStr = fileName.substring(0, fileName.indexOf("."));
			}
			fileLists = farmFileManagerImpl.getFilesByName(queryNameStr);
			page.putAttr("VERSIONS", fileLists);
			page.putAttr("isShowAllVersion", true);
		} catch (DocNoExistException e) {
			log.error(e.getMessage());
			farmFileIndexManagerImpl.delFileLucenneIndex(fileid);
			return ViewMode.getInstance().setError(e.toString())
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.toString())
					.returnModelAndView(ThemesUtil.getThemePath() + "/error");
		}
		return page.returnModelAndView(ThemesUtil.getThemePath() + "/webfile/file");
	}
}
