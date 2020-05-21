/**
 * 
 */
package com.example.jdbcexample;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.jdbcexample.model.Account;
import com.example.jdbcexample.repository.IAccountRepository;

/**
 * @author ralph
 *
 */
@Component
public class CallFunction {

	private SimpleJdbcCall simpleJdbcCallFunction;
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	public CallFunction() {
		// TODO Auto-generated constructor stub
	}
	
	public void callStoreProcedureExample() 
	{
		System.out.println("CallFunction.callStoreProcedureExample()");
		callStoreProcedureUsingNamedStoredProcedureQueryInEntity();
		callStoreProcedureUsingProcedureQInRepository();
		callStoreProcedureUsingDinamicStoredProcedureQuery();
	}
	
	public void callStoreProcedureUsingNamedStoredProcedureQueryInEntity() {
		System.out.println("CallFunction.callStoreProcedureUsingNamedStoredProcedureQueryInEntity()");
		System.out.println("Account in BD");
		accountRepository.findAll().stream().forEach(System.out::println);
		
		System.out.println("Update Balance With Interest for all accounts");
		TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
	        @Override
	        protected void doInTransactionWithoutResult(TransactionStatus status) {
	        	 StoredProcedureQuery procedureQuery =
	   	              entityManager.createNamedStoredProcedureQuery("Account.updateBalanceWithInterest")
	   	             .setParameter("rate", 0.04);
	   	     
	   	     System.out.println("Account updated :"+procedureQuery.getSingleResult()+" using rate = 0.04");
	        }
	    });
		
	      
		System.out.println("Account after update balance process"); 
	      accountRepository.findAll().stream().forEach(System.out::println);
		
		
	}
	
public void callStoreProcedureUsingProcedureQInRepository() {
		System.out.println("CallFunction.callStoreProcedureUsingProcedureQInRepository()");
		System.out.println("Account in BD");
		accountRepository.findAll().stream().forEach(System.out::println);
		
		System.out.println("Update Balance With Interest for all accounts");
		 
		System.out.println("Account updated :"+accountRepository.applyInteresRate(0.5)+" using rate = 0.05");
		
	      
		System.out.println("Account after update balance process"); 
	      accountRepository.findAll().stream().forEach(System.out::println);
		
		
	}
	
public void callStoreProcedureUsingDinamicStoredProcedureQuery() {
	System.out.println("CallFunction.callStoreProcedureUsingDinamicStoredProcedureQuery()");
	System.out.println("Account in BD");
	accountRepository.findAll().stream().forEach(System.out::println);
	
	System.out.println("Update Balance With Interest for all accounts");
	TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
	transactionTemplate.execute(new TransactionCallbackWithoutResult() {
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus status) {
        	 StoredProcedureQuery procedureQuery =
   	              entityManager.createStoredProcedureQuery("sum_interest_rate")
   	              .registerStoredProcedureParameter("rate", Double.class, ParameterMode.IN)
   	              .setParameter("rate", 0.03);
   	     
   	     System.out.println("Account updated :"+procedureQuery.getSingleResult()+" using rate = 0.03");
        }
    });
	
      
	 System.out.println("Account after update balance process"); 
      accountRepository.findAll().stream().forEach(System.out::println);
	
	
} 
}
