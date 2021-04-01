package br.unit.web.domain;

import javax.persistence.Id;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BancoRepository extends CrudRepository<Banco, String> {

	Banco findByConta(String conta);

}
