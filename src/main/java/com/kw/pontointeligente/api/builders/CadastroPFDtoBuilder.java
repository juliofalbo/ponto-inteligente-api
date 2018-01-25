package com.kw.pontointeligente.api.builders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kw.pontointeligente.api.dtos.CadastroPFDto;
import com.kw.pontointeligente.api.entities.Funcionario;

/**
 * Design Pattern Builder
 * 
 * Separar a construção de um objeto complexo de sua representação de modo que o
 * mesmo processo de construção possa criar diferentes representações.
 *
 */

// Implementando o padrão de projeto Builder com o objetivo de facilitar os
// testes
public class CadastroPFDtoBuilder {

	private List<CadastroPFDto> pjs = new ArrayList<CadastroPFDto>();

	private CadastroPFDtoBuilder(CadastroPFDto lancamento) {
		pjs.add(lancamento);

	}

	public static CadastroPFDtoBuilder newPFDto(Funcionario funcionario) {

		Optional<Float> qtdHorasAlmocoOpt = funcionario.getQtdHorasAlmocoOpt();
		Optional<Float> qtdHorasTrabalhoDiaOpt = funcionario.getQtdHorasTrabalhoDiaOpt();
		Optional<BigDecimal> valorHoraOpt = funcionario.getValorHoraOpt();

		Optional<String> qtdHorasAlmocoStr = Optional
				.of(qtdHorasAlmocoOpt.isPresent() ? qtdHorasAlmocoOpt.get().toString() : "");
		Optional<String> qtdHorasTrabalhoDiaStr = Optional
				.of(qtdHorasTrabalhoDiaOpt.isPresent() ? qtdHorasTrabalhoDiaOpt.get().toString() : "");
		Optional<String> valorHoraStr = Optional.of(valorHoraOpt.isPresent() ? valorHoraOpt.get().toString() : "");

		CadastroPFDto dto = create(funcionario.getId(), funcionario.getNome(), funcionario.getEmail(),
				funcionario.getCpf(), funcionario.getEmpresa().getCnpj(), qtdHorasAlmocoStr, qtdHorasTrabalhoDiaStr,
				valorHoraStr);
		return new CadastroPFDtoBuilder(dto);
	}

	public static CadastroPFDtoBuilder newPFDto(String nome, String email, String cpf, String cnpj) {
		CadastroPFDto dto = create(null, nome, email, cpf, cnpj, Optional.empty(), Optional.empty(), Optional.empty());
		return new CadastroPFDtoBuilder(dto);
	}

	private static CadastroPFDto create(Long id, String nome, String email, String cpf, String cnpj,
			Optional<String> qtdHoraAlmoco, Optional<String> qtdHorasTrabalhoDia, Optional<String> valorHora) {
		CadastroPFDto cadastroPFDto = new CadastroPFDto();
		cadastroPFDto.setId(id);
		cadastroPFDto.setNome(nome);
		cadastroPFDto.setEmail(email);
		cadastroPFDto.setCpf(cpf);
		cadastroPFDto.setCnpj(cnpj);
		cadastroPFDto.setQtdHorasAlmoco(qtdHoraAlmoco);
		cadastroPFDto.setQtdHorasTrabalhoDia(qtdHorasTrabalhoDia);
		cadastroPFDto.setValorHora(valorHora);
		return cadastroPFDto;
	}

	public CadastroPFDtoBuilder more(int number) {
		CadastroPFDto base = pjs.get(0);
		for (int i = 0; i < number; i++) {
			pjs.add(newPFDto(base.getNome(), base.getEmail(), base.getCpf(), base.getCnpj()).buildOne());
		}
		return this;
	}

	public CadastroPFDto buildOne() {
		return pjs.get(0);
	}

	public List<CadastroPFDto> buildAll() {
		return pjs;
	}
}