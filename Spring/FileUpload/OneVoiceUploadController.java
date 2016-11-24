//package com.onevoiceupload.controllers;
// 

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.onevoiceupload.bean.FileBean;
import com.onevoiceupload.service.ImportServiceImpl;

@Controller
public class OneVoiceUploadController implements HandlerExceptionResolver {
	
	private static final Logger logger = Logger.getLogger(OneVoiceUploadController.class);
	
	@Autowired
	@Qualifier("importServiceImpl")
	private ImportServiceImpl importServiceImpl;
	
	
	@RequestMapping(value="/OneVoiceUpload.htm",method=RequestMethod.GET)
	public String showForm(ModelMap model){
		logger.info("*******************Inside showForm************************ ");
		FileBean form = new FileBean();
		model.addAttribute("fileBean", form);
		return "OneVoiceUpload";
	}
	
	@RequestMapping(value="/ExcelDataUpload.htm",method=RequestMethod.POST)
	public String processForm(@ModelAttribute(value="fileBean") FileBean fileBean,BindingResult result,ModelMap model) throws Exception{
		logger.info("**********************Inside Excel Data Upload *****************");
		if(!result.hasErrors()){
        importServiceImpl.importExcelData(fileBean);
	}
		String msg="File uploaded successfully. Required Table is updated with the new data";
		model.addAttribute("success", msg);
		
		//return new ModelAndView("/OneVoiceUpload", (Map) msg);
        return "OneVoiceUpload";
    }

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception exception) {
		Map<Object, Object> model = new HashMap<Object, Object>();
        if (exception instanceof MaxUploadSizeExceededException)
        {
            model.put("errors", "File size should be less then "+((MaxUploadSizeExceededException)exception).getMaxUploadSize()+" byte.");
            logger.error("Error Occured", exception) ;
        } else if (exception instanceof IllegalArgumentException)
        {
            model.put("errors", "Unexpected error: " + exception.getMessage());
                  logger.error("Error Occured", exception);
        }
        else if (exception instanceof DataIntegrityViolationException){
        	model.put("errors", "Unexpected error: Excel Data Issue  ");
            logger.error("Error Occured", exception);
        }
        else{
        	model.put("errors", "Unexpected error  ");
            logger.error("Error Occured", exception);
        }
        model.put("fileBean", new FileBean());
        return new ModelAndView("/OneVoiceUpload", (Map) model);

	}
	
	
}
