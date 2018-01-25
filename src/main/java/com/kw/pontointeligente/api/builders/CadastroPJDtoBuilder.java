package com.kw.pontointeligente.api.builders;

import java.util.ArrayList;
import java.util.List;

import com.kw.pontointeligente.api.dtos.CadastroPJDto;
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
public class CadastroPJDtoBuilder {

	private List<CadastroPJDto> pjs = new ArrayList<CadastroPJDto>();

	private CadastroPJDtoBuilder(CadastroPJDto lancamento) {
		pjs.add(lancamento);

	}

	public static CadastroPJDtoBuilder newPJDto(Funcionario funcionario) {
		CadastroPJDto dto = create(funcionario.getId(), funcionario.getNome(), funcionario.getEmail(),
				funcionario.getCpf(), funcionario.getEmpresa().getRazaoSocial(), funcionario.getEmpresa().getCnpj());
		return new CadastroPJDtoBuilder(dto);
	}

	public static CadastroPJDtoBuilder newPJDto(String nome, String email, String cpf, String razaoSocial,
			String cnpj) {
		CadastroPJDto dto = create(null, nome, email, cpf, razaoSocial, cnpj);
		return new CadastroPJDtoBuilder(dto);
	}

	private static CadastroPJDto create(Long id, String nome, String email, String cpf, String razaoSocial,
			String cnpj) {
		CadastroPJDto cadastroPJDto = new CadastroPJDto();
		cadastroPJDto.setId(id);
		cadastroPJDto.setNome(nome);
		cadastroPJDto.setEmail(email);
		cadastroPJDto.setCpf(cpf);
		cadastroPJDto.setRazaoSocial(razaoSocial);
		cadastroPJDto.setCnpj(cnpj);
		return cadastroPJDto;
	}

	public CadastroPJDtoBuilder more(int number) {
		CadastroPJDto base = pjs.get(0);
		for (int i = 0; i < number; i++) {
			pjs.add(newPJDto(base.getNome(), base.getEmail(), base.getCpf(), base.getRazaoSocial(), base.getCnpj()).buildOne());
		}
		return this;
	}

	public CadastroPJDto buildOne() {
		return pjs.get(0);
	}

	public List<CadastroPJDto> buildAll() {
		return pjs;
	}
}