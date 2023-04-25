package br.senai.sc.dto;

import java.time.Instant;

import org.springframework.stereotype.Service;

import br.senai.sc.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	private Long id;
	private Double value;
	private Instant instant;
	
	public TransactionDTO(Transaction transaction) {
		this.id = transaction.getId();
		this.instant = transaction.getInstant();
		this.value = transaction.getValue();
	}
}
