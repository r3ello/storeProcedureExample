/**
 * 
 */
package com.example.jdbcexample.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.example.jdbcexample.model.Account;


/**
 * @author ralph
 *
 */
public interface IAccountRepository extends JpaRepository<Account, Integer> {

	@Procedure(procedureName="sum_interest_rate")
	public Integer applyInteresRate(Double rate);
}
