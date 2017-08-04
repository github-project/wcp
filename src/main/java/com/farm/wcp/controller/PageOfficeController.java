/**  
 * @Title: PageOfficeController.java 
 * @Package com.farm.wcp.controller 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author <作者姓名>  
 * @date 2017-7-3下午2:19:24
*/
package com.farm.wcp.controller;


import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.page.ViewMode;
import com.farm.core.time.TimeTool;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.exception.DocNoExistException;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_TYPE;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.parameter.FarmParameterService;
import com.farm.web.WebUtils;
import com.zhuozhengsoft.pageoffice.BorderStyleType;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.ThemeType;

@RequestMapping("/pageoffice")
@Controller
public class PageOfficeController extends WebUtils{
	private final static Logger log = Logger.getLogger(PageOfficeController.class);
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	
	@RequestMapping("/openfile")
    public ModelAndView openfile(String fileId,HttpSession session, HttpServletRequest request) throws Exception {  
		log.info("Open and Edit word file!");
		
		FarmDocfile file = farmFileManagerImpl.getFile(fileId);
		if (file == null) {
			throw new DocNoExistException();
		}
		//===========================================================
		//获取系统中存的文件,以.file结尾
		String path = FarmDocFiles.getFileDirPath()+file.getDir()+file.getFilename();
		File tmpFile = new File(path);
		//复制文件,复制成office文件
		String rootPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
		String copyFilePath = rootPath+File.separator+"fileBak"+File.separator+ file.getFilename().substring(0, file.getFilename().lastIndexOf("."))+file.getExname();
		log.info("copyFilePath:"+copyFilePath);
		//获取要打开文件的相对位置//fileBak是写死的,如果文件夹不存在，则创建
		String dir = rootPath+File.separator+"fileBak";
		File copyfile = new File(dir);
		if (!copyfile.exists()) {
			copyfile.mkdirs();
        }
		copyFile(tmpFile, copyFilePath);
//		String path1 = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path") +request.getContextPath() ;
//		String      path2=File.separator + "fileBak" + File.separator + file.getFilename().substring(0, file.getFilename().lastIndexOf("."))+file.getExname();
//			    log.info("copyFilePath:" + path1+path2);
//			    
//				String dir = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path") + 
//					      request.getContextPath() +File.separator+"fileBak";
//				File copyfile = new File(dir);
//				if (!copyfile.exists()) {
//					copyfile.mkdirs();
//		        }
//				
//				String copyFilePath=path1+path2;
//			    copyFile(tmpFile, copyFilePath);

		//获取当前用户信息
		LoginUser user = getCurrentUser(session);
		//===========================================================
		if(!file.getExname().equals(".pdf")){
			PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
			poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); // 此行必须
		
			// 设置标题
			poCtrl1.setCaption(file.getName());
			// 创建工具条
			String fileNameStr = file.getFilename().substring(0, file.getFilename().indexOf("."));
			boolean saveFlag = true;
			if(fileNameStr.contains("_") && fileNameStr.substring(fileNameStr.lastIndexOf("_")+1).length()==14){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				try {
					sdf.parse(fileNameStr.substring(fileNameStr.lastIndexOf("_")+1));
					saveFlag = false;
				} catch (Exception e) {
					saveFlag = true;
				}
			}
			if(saveFlag){
				poCtrl1.addCustomToolButton("保存", "SaveDocument()", 1);
				poCtrl1.setSaveFilePage(request.getContextPath()+"/pageoffice/savefile.do?fileId="+fileId);
			}
			poCtrl1.addCustomToolButton("-", "", 0);
			poCtrl1.addCustomToolButton("打印", "ShowPrintDlg()", 6);
			poCtrl1.addCustomToolButton("-", "", 0);
			poCtrl1.addCustomToolButton("全屏切换", "SetFullScreen()", 4);
			poCtrl1.addCustomToolButton("-", "", 0);
			if(file.getExname().equals(".xlsx") || file.getExname().equals(".xls") || file.getExname().equals(".csv")){
				poCtrl1.webOpen(copyFilePath, OpenModeType.xlsNormalEdit, user.getName());
			}
			if(file.getExname().equals(".doc") || file.getExname().equals(".docx")){
				poCtrl1.webOpen(copyFilePath, OpenModeType.docNormalEdit, user.getName());
			}
			if(file.getExname().equals(".ppt") || file.getExname().equals(".pptx")){
				poCtrl1.webOpen(copyFilePath, OpenModeType.pptNormalEdit, user.getName());
			}
			poCtrl1.setTagId("PageOfficeCtrl1"); // 此行必须
			return ViewMode.getInstance().returnModelAndView("/openoffice/editword");
		}else{//如果是pdf文件
			PDFCtrl pdf = new PDFCtrl(request);
			pdf.setServerPage(request.getContextPath() + "/poserver.zz");
			System.out.println(request.getContextPath());
			// 设置界面样式
			pdf.setBorderColor(Color.RED);
			pdf.setCaption(file.getName());
			pdf.setBorderStyle(BorderStyleType.BorderFlat);
			pdf.setTheme(ThemeType.Office2010);
			//设置菜单选项
			pdf.addCustomToolButton("打印", "Print()", 6);
			pdf.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
			pdf.addCustomToolButton("-", "", 0);
			//搜索设置
			pdf.addCustomToolButton("搜索", "SearchText()", 0);
			pdf.addCustomToolButton("搜索下一个", "SearchTextNext()", 0);
			pdf.addCustomToolButton("搜索上一个", "SearchTextPrev()", 0);
			pdf.addCustomToolButton("实际大小", "SetPageReal()", 16);
			pdf.addCustomToolButton("适合页面", "SetPageFit()", 17);
			pdf.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
			pdf.addCustomToolButton("-", "", 0);
			pdf.addCustomToolButton("首页", "FirstPage()", 8);
			pdf.addCustomToolButton("上一页", "PreviousPage()", 9);
			pdf.addCustomToolButton("下一页", "NextPage()", 10);
			pdf.addCustomToolButton("尾页", "LastPage()", 11);
			pdf.addCustomToolButton("-", "", 0);
			// 打开PDF文档
			pdf.webOpen(copyFilePath);
			pdf.setTagId("PDFCtrl1");
			return ViewMode.getInstance().returnModelAndView("/openoffice/openPDF");
		}
    }  
	@RequestMapping("/savefile")
    public ModelAndView saveword(String fileId,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception {
		FileSaver fs = new FileSaver(request, response);
		request.setAttribute("FileSaver", fs);
		FarmDocfile file = farmFileManagerImpl.getFile(fileId);
		if (file == null) {
			throw new DocNoExistException();
		}
		String webPath = FarmDocFiles.getFileDirPath();
		System.out.println("文件目录"+webPath);
		String path = webPath+file.getDir()+file.getFilename();
		File oldFile = new File(path);
		System.out.println("文件全路径"+path);
		//备份原来的文件，作为查看历史时使用
		/*因为开源版本和免费版本的数据结构不一样，也不知道免费版本的源码，所以尽量不要改动原有代码和数据结构.
		*关于附件分支功能，该表中没有其它字段可以标识多条数据是指向同一个附件，
		*所以目前只能通过filename加上后缀来识别,filename日期之前部分一致即为相同附件.
		*如果以后有了免费版本的源码，可以自由的修改数据结构的时候，可以考虑换成其它方便的方式
		*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = file.getFilename();
		String newFileName = fileName.substring(0, fileName.indexOf("."))+"_"+sdf.format(new Date())+fileName.substring(fileName.indexOf("."));
		String bakPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path")+File.separator+"fileBak"+File.separator;
		String newPath  = bakPath+ newFileName;
		System.out.println("新路径"+newPath);
		File newFile = new File(newPath);
		//拷贝原来文件到备份
		FileUtils.copyFile(oldFile, newFile);
		//保存历史版本
		farmFileManagerImpl.saveFile(newFile, FILE_TYPE.RESOURCE_FILE, file.getName(), getCurrentUser(session));
		//删除备份文件
		newFile.delete();
		//更新最新的文件etime
		farmFileManagerImpl.submitFile(fileId);
		//保存文件置原来位置，文件名不变
		fs.saveToFile(path);
		return ViewMode.getInstance().returnModelAndView("/openoffice/savefile");
    }  
	/**
	 * 拷贝一个文件到新的地址
	 * 
	 * @param file
	 * @param newPath
	 */
	public static void copyFile(File file, String newPath) {
		int byteread = 0;
		File oldfile = file;
		if (oldfile.exists()) { // 文件存在时
			InputStream inStream = null;
			FileOutputStream fs = null;
			try {
				inStream = new FileInputStream(file);
				fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			} finally {
				try {
					inStream.close();
					fs.flush();
					fs.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
