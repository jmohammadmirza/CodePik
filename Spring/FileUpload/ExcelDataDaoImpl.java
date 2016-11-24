package com.onevoiceupload.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.onevoiceupload.bean.ExcelDataRow;

public abstract class ExcelDataDaoImpl extends JdbcDaoSupport implements  ExcelDataDao{
	
	private static final Logger logger = Logger.getLogger(ExcelDataDao.class);
	
	@Autowired  
	private DataSource dataSource; 
	int updateFlag;
	@Override
	public int deleteExcelData() {
		logger.info("**********************************Deleting Records Start**********************************");
		// TODO Auto-generated method stub
		//NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		//String sql="DELETE  FROM WRK7.GLBL_ONEVOICE WHERE P_SUPLR_ONE_VOICE=:supplierScore";
		String delQuery="DELETE  FROM WRK7.GLBL_ONEVOICE";
		//Map<String,Integer> paramMap= new HashMap<String,Integer>();
		//paramMap.put("supplierScore", 95);
		//namedParameterJdbcTemplate.update(sql, null );
		// updateFlag=namedParameterJdbcTemplate.update(sql, paramMap);
		// namedParameterJdbcTemplate.ex
		updateFlag= jdbcTemplate.update(delQuery);
		logger.info("**********************************Deleting Records End**************************************");
		return updateFlag;
	}

	@Override
	public void insertExcelData(Map<Integer, ExcelDataRow> map) {
		logger.info("**********************************Insering Records Start**********************************");
		List<ExcelDataRow> datalist=new ArrayList<ExcelDataRow>();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		 String insertQuery = "INSERT INTO WRK7.GLBL_ONEVOICE  " +
		"(D_YR,D_MO, I_ORG_CODE,P_SUPLR_ONE_VOICE) VALUES (:year, :month, :orgCode,:supplierScore)";
		
		for (Map.Entry<Integer, ExcelDataRow> entry : map.entrySet()) {
				datalist.add(entry.getValue());
		}
		
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(datalist.toArray());
		logger.info("**********************************Executing Batch Insert************************************");
		namedParameterJdbcTemplate.batchUpdate(insertQuery, params);
		logger.info("**********************************Batch Insert Completed**************************************");
		
	}

	@Override
	public void deleteAndUpdateData(Map<Integer, ExcelDataRow> map) throws Exception {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		List<ExcelDataRow> datalist=new ArrayList<ExcelDataRow>();
		String selQuery="SELECT D_YR, D_MO, I_ORG_CODE, P_SUPLR_ONE_VOICE FROM WRK7.GLBL_ONEVOICE ";
		String delQuery="DELETE  FROM WRK7.GLBL_ONEVOICE";
		String insertQuery = "INSERT INTO WRK7.GLBL_ONEVOICE  " +
		"(D_YR,D_MO, I_ORG_CODE,P_SUPLR_ONE_VOICE) VALUES (:year, :month, :orgCode,:supplierScore)";
		
		
		logger.info("**********************************Transaction Starts**************************************");
		if(!jdbcTemplate.queryForList(selQuery).isEmpty()){
			//Delete if not empty
			logger.info("**********************************Deleting Records Start**********************************");
			updateFlag= jdbcTemplate.update(delQuery);
			logger.info("**********************************Deleting Records End**************************************");	
		}
		//if (updateFlag!=0){
		logger.info("**********************************Insering Records Start**********************************");
		
	   for (Map.Entry<Integer, ExcelDataRow> entry : map.entrySet()) {
				datalist.add(entry.getValue());
		}
		
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(datalist.toArray());
		logger.info("**********************************Executing Batch Insert************************************");
		namedParameterJdbcTemplate.batchUpdate(insertQuery, params);
		logger.info("**********************************Batch Insert Completed**************************************");
			
		//}
		logger.info("**********************************Transaction Ends**************************************");
	}

}
