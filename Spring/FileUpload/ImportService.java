package com.onevoiceupload.service;

import org.springframework.transaction.annotation.Transactional;

import com.onevoiceupload.bean.FileBean;
@Transactional
public interface ImportService {
	 public void importExcelData(FileBean fileBean) throws Exception;
}
