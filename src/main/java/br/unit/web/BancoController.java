package br.unit.web;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.unit.web.domain.Banco;
import br.unit.web.domain.BancoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/banco")
@Api(value="Banco API REST")
@CrossOrigin(origins="*")
public class BancoController {

	@Autowired
	BancoRepository bancoRepository;

	@PostMapping("/abrirConta")
	@ApiOperation(value="Abri uma conta no banco")
	public ResponseEntity<Object> CriarConta(@RequestBody Banco banco) {
		try {
			int valorinsert = banco.getValor();

			banco.setValorAnterior(valorinsert);
			Banco contaSalva = bancoRepository.save(banco);

			return ResponseEntity.ok("Conta: " + banco.getConta() + " - Saldo Atual: " + banco.getValor() + " R$");

		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.badRequest().body(false);
		}

	}

	@PutMapping("/depositar")
	@ApiOperation(value="deposita na conta no banco")
	public ResponseEntity<Object> depositoBanco(@RequestBody Banco banco) {

		try {
			// Receber as informacoes da conta
			Banco conta = bancoRepository.findByConta(banco.getConta());

			// Recebe o valor anterior da conta
			int valorAnterior = conta.getValorAnterior();

			// Recebe o valor passado no body
			int valorAtual = banco.getValor();

			// Gera o novo valor do saldo
			int valorNovo = (valorAtual + valorAnterior);

			// Seta o novo valor
			banco.setValor(valorNovo);

			// Salva o novo valor para futuros depositos
			banco.setValorAnterior(valorNovo);

			Banco valorSalvo = bancoRepository.save(banco);

			return ResponseEntity.ok("Deposito realizado com sucesso");
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.badRequest().body(false);
		}

	}

	@GetMapping("/saldo/{conta}")
	@ApiOperation(value="Verifica o saldo da conta")
	public Object saldoConta(@PathVariable(value = "conta") String conta) {
		try {
			Banco banco = bancoRepository.findByConta(conta);

			return ResponseEntity.ok("Conta: " + banco.getConta() + " - Saldo Atual: " + banco.getValor() + " R$");
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.badRequest().body("Conta não encontrada");
		}
	}

	@PutMapping("/sacar")
	@ApiOperation(value="Saca um valor da conta")
	public ResponseEntity<Object> sacarBanco(@RequestBody Banco banco) {
		try {
			Banco conta = bancoRepository.findByConta(banco.getConta());

			int valorAnterior = conta.getValorAnterior();
			if (valorAnterior > 0 && valorAnterior > banco.getValor()) {

				int valorAtual = banco.getValor();

				int valorNovo = (valorAnterior - valorAtual);

				banco.setValor(valorNovo);

				banco.setValorAnterior(valorNovo);

				Banco valorSalvo = bancoRepository.save(banco);

				return ResponseEntity.ok("Valor Retirado com sucesso");
			} else if (valorAnterior < banco.getValor()) {
				return ResponseEntity.ok("Não há saldo suficiente");
			} else {
				return ResponseEntity.ok("Não há saldo suficiente");
			}
		} catch (Exception e) {

			e.printStackTrace();
			return ResponseEntity.badRequest().body(false);
		}
	}
}
