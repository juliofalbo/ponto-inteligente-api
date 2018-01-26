package com.kw.pontointeligente.api.builders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.kw.pontointeligente.api.dtos.LancamentoDto;
import com.kw.pontointeligente.api.entities.Lancamento;
import com.kw.pontointeligente.api.enums.TipoEnum;

/**
 * Design Pattern Builder
 * 
 * Separar a construção de um objeto complexo de sua representação de modo que o
 * mesmo processo de construção possa criar diferentes representações.
 *
 */

// Implementando o padrão de projeto Builder com o objetivo de facilitar os
// testes
public class LancamentoDtoBuilder {

	private List<LancamentoDto> lancamentos = new ArrayList<LancamentoDto>();
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private LancamentoDtoBuilder(LancamentoDto lancamento) {
		lancamentos.add(lancamento);
	}

	public static LancamentoDtoBuilder newLancamentoDto(Lancamento lancamento) {
		LancamentoDto lancamentoDto = create(lancamento.getId(), lancamento.getData(), lancamento.getTipo(),
				lancamento.getDescricao(), lancamento.getLocalizacao(), lancamento.getFuncionario().getId());
		return new LancamentoDtoBuilder(lancamentoDto);
	}

	private static LancamentoDto create(Long id, Date data, TipoEnum tipo, String descricao, String localizacao,
			Long funcionarioId) {
		LancamentoDto lancamentoDto = new LancamentoDto();
		lancamentoDto.setId(Optional.of(id));
		lancamentoDto.setData(dateFormat.format(data));
		lancamentoDto.setTipo(tipo.toString());
		lancamentoDto.setDescricao(descricao);
		lancamentoDto.setLocalizacao(localizacao);
		lancamentoDto.setFuncionarioId(funcionarioId);
		return lancamentoDto;
	}

	public LancamentoDto buildOne() {
		return lancamentos.get(0);
	}

	public List<LancamentoDto> buildAll() {
		return lancamentos;
	}
}