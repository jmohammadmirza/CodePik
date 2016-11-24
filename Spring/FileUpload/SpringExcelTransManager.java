package com.onevoiceupload.transaction;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.onevoiceupload.bean.ExcelDataRow;
import com.onevoiceupload.dao.ExcelDataDao;
import com.onevoiceupload.dao.ExcelDataDaoImpl;

public class SpringExcelTransManager extends ExcelDataDaoImpl implements ExcelDataDao{
	private static final Logger logger = Logger.getLogger(SpringExcelTransManager.class);
	private PlatformTransactionManager  transactionManager;
	public void setTransactionManager(PlatformTransactionManager txManager) {
		        this.transactionManager = txManager;
		    }

	@Override
	public void initiateTransactionExcelUpdate( Map<Integer, ExcelDataRow> map)
			throws Exception {
		logger.info("********Executing the Transaction: Spring Transaction Template*********");
		
		TransactionDefinition txDef = new DefaultTransactionDefinition();
		TransactionStatus txStatus = transactionManager.getTransaction(txDef);
		try{
				deleteAndUpdateData(map);
				transactionManager.commit(txStatus);
		}catch (Exception e) {
			    logger.error("Error occured while executing the transaction", e);
	            transactionManager.rollback(txStatus);
	            throw e;
        }
	
//		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
//			
//
//			@Override
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
//				try {
//					deleteAndUpdateData(map);
//				} catch (Exception e) {
//				 	e.printStackTrace();
//					logger.error("Error occured while executing the transaction", e);
//				}
//			}
//		});
	}
}
