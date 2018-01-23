package com.kw.pontointeligente.api.builders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kw.pontointeligente.api.entities.Funcionario;
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
public class LancamentoBuilder {

	private List<Lancamento> lancamentos = new ArrayList<>();

	private LancamentoBuilder(Lancamento lancamento) {
		lancamentos.add(lancamento);

	}

	public static LancamentoBuilder newLancamento(Date data, String descricao, String localizacao, TipoEnum tipo,
			Funcionario funcionario) {
		Lancamento lancamento = create(data, descricao, localizacao, tipo, funcionario);
		return new LancamentoBuilder(lancamento);
	}

	public static LancamentoBuilder newLancamento(TipoEnum tipo, Funcionario funcionario) {
		Lancamento lancamento = create(new Date(), "Descrição Test", "Localização Test", tipo, funcionario);
		return new LancamentoBuilder(lancamento);
	}
	
	public static LancamentoBuilder newLancamento(Date data, TipoEnum tipo, Funcionario funcionario) {
		Lancamento lancamento = create(data, "Descrição Test", "Localização Test", tipo, funcionario);
		return new LancamentoBuilder(lancamento);
	}

	private static Lancamento create(Date data, String descricao, String localizacao, TipoEnum tipo,
			Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(data);
		lancamento.setDescricao(descricao);
		lancamento.setLocalizacao(localizacao);
		lancamento.setTipo(tipo);
		lancamento.setFuncionario(funcionario);
		return lancamento;
	}

	public LancamentoBuilder more(int number) {
		Lancamento base = lancamentos.get(0);
		for (int i = 0; i < number; i++) {
			lancamentos.add(newLancamento(base.getData(), "Descrição Test " + i, "Localização Test " + i,
					base.getTipo(), base.getFuncionario()).buildOne());
		}
		return this;
	}

	public Lancamento buildOne() {
		return lancamentos.get(0);
	}

	public List<Lancamento> buildAll() {
		return lancamentos;
	}
}