package com.kw.pontointeligente.api.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.kw.pontointeligente.api.dtos.FuncionarioDto;
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
public class FuncionarioDtoBuilder {

	private List<FuncionarioDto> funcionarios = new ArrayList<FuncionarioDto>();

	private FuncionarioDtoBuilder(FuncionarioDto funcionario) {
		funcionarios.add(funcionario);
	}

	public static FuncionarioDtoBuilder newFuncionario(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = create(funcionario);
		return new FuncionarioDtoBuilder(funcionarioDto);
	}

	private static FuncionarioDto create(Funcionario funcionario) {

		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setNome(funcionario.getNome());
		
		funcionario.getQtdHorasAlmocoOpt().ifPresent(
				qtdHorasAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
				qtdHorasTrabDia -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabDia))));
		funcionario.getValorHoraOpt()
				.ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));

		return funcionarioDto;
	}

	public FuncionarioDto buildOne() {
		return funcionarios.get(0);
	}

	public List<FuncionarioDto> buildAll() {
		return funcionarios;
	}

}