/**
 * 
 */
package com.example.jdbcexample.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.ParameterMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ralph
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@NamedStoredProcedureQuery(name = "Account.updateBalanceWithInterest", 
procedureName = "sum_interest_rate", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN, name = "rate", type = Double.class)})
public class Account {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
     
    @Column(nullable = false)
    private String name;
    
    @Column(nullable =false)
    private Double balance;
    
}
