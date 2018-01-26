package com.kw.pontointeligente.api.builders;

import java.util.ArrayList;
import java.util.List;

import com.kw.pontointeligente.api.dtos.EmpresaDto;
import com.kw.pontointeligente.api.entities.Empresa;

/**
 * Design Pattern Builder
 * 
 * Separar a construção de um objeto complexo de sua representação de modo que o
 * mesmo processo de construção possa criar diferentes representações.
 *
 */

// Implementando o padrão de projeto Builder com o objetivo de facilitar os
// testes
public class EmpresaDtoBuilder {

	private List<EmpresaDto> empresas = new ArrayList<EmpresaDto>();

	private EmpresaDtoBuilder(EmpresaDto empresa) {
		empresas.add(empresa);

	}
	public static EmpresaDtoBuilder newEmpresaDto(Empresa empresa) {
		EmpresaDto empresaDto = create(empresa.getId(), empresa.getRazaoSocial(), empresa.getCnpj());
		return new EmpresaDtoBuilder(empresaDto);
	}
	
	public static EmpresaDtoBuilder newEmpresaDto(String razaoSocial, String cnpj) {
		EmpresaDto empresaDto = create(null, razaoSocial, cnpj);
		return new EmpresaDtoBuilder(empresaDto);
	}

	public static EmpresaDtoBuilder newEmpresa() {
		EmpresaDto empresaDto = create(null, "Empresa Test", "51463645000100");
		return new EmpresaDtoBuilder(empresaDto);
	}

	private static EmpresaDto create(Long id, String razaoSocial, String cnpj) {
		EmpresaDto empresa = new EmpresaDto();
		empresa.setId(id);
		empresa.setRazaoSocial(razaoSocial);
		empresa.setCnpj(cnpj);
		return empresa;
	}

	public EmpresaDtoBuilder more(int number) {
		EmpresaDto base = empresas.get(0);
		for (int i = 0; i < number; i++) {
			empresas.add(create(null, "Empresa Test " + i, base.getCnpj()));
		}
		return this;
	}

	public EmpresaDto buildOne() {
		return empresas.get(0);
	}

	public List<EmpresaDto> buildAll() {
		return empresas;
	}
}