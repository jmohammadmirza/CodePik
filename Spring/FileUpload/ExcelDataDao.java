//package com.onevoiceupload.dao;

import java.util.Map;

import com.onevoiceupload.bean.ExcelDataRow;

public interface ExcelDataDao {
	public void insertExcelData(Map<Integer, ExcelDataRow> map);
	public int deleteExcelData();
	public void deleteAndUpdateData(Map<Integer, ExcelDataRow> map) throws Exception;
    public void initiateTransactionExcelUpdate(Map<Integer, ExcelDataRow> map)throws Exception;
}
