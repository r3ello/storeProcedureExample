# Stored Procedures Example Spring Boot
Example using differents way to call  Stored Procedures in Java
# Dependencies
```
<dependency>
	<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
<dependency>
		<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>		
<dependency>
	<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
		<scope>runtime</scope>
</dependency>
```
## Postgres function to increment balance using interest rate
```
CREATE OR REPLACE FUNCTION public.sum_interest_rate ( double precision)
RETURNS integer AS $body$
DECLARE
    affected_rows integer;
BEGIN
 UPDATE account set balance = balance+(balance * $1);
 GET DIAGNOSTICS affected_rows = ROW_COUNT;
 RETURN affected_rows;
END;
$body$
LANGUAGE 'plpgsql'
VOLATILE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;
```
## Call stored procedure using StoredProcedureQuery
- Can assigned stored procedure to model class example:
```
@Entity
@NamedStoredProcedureQuery(name = "Account.updateBalanceWithInterest", 
procedureName = "sum_interest_rate", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "rate", type = Double.class)})
public class Account {.....}
```
- And call this stored procedure using StoredProcedureQuery
```
public void callStoreProcedureUsingNamedStoredProcedureQueryInEntity() {
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
```
# Call stored procedure using Repository
- Use @Procedure to refer your stored procedure 
```
public interface IAccountRepository extends JpaRepository<Account, Integer> {

	@Procedure(procedureName="sum_interest_rate")
	public Integer applyInteresRate(Double rate);
}
```
And call stored procedure using your repository
```
public void callStoreProcedureUsingProcedureQInRepository() {
		
		accountRepository.findAll().stream().forEach(System.out::println);
		
		System.out.println("Account updated :"+accountRepository.applyInteresRate(0.5)+" using rate = 0.05");	
	      
		System.out.println("Account after update balance process"); 
	      accountRepository.findAll().stream().forEach(System.out::println);	
		
	}
```
## Call stored procedure using Dynamic StoredProcedureQuery
```
public void callStoreProcedureUsingDinamicStoredProcedureQuery() {
	accountRepository.findAll().stream().forEach(System.out::println);
	
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
```

