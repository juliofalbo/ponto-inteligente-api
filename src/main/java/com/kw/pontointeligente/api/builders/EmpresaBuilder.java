package com.kw.pontointeligente.api.builders;

import java.util.ArrayList;
import java.util.List;

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
public class EmpresaBuilder {

	private List<Empresa> empresas = new ArrayList<Empresa>();

	private EmpresaBuilder(Empresa empresa) {
		empresas.add(empresa);

	}

	public static EmpresaBuilder newEmpresa(Long id, String razaoSocial, String cnpj) {
		Empresa empresa = create(id, razaoSocial, cnpj);
		return new EmpresaBuilder(empresa);
	}
	
	public static EmpresaBuilder newEmpresa(String razaoSocial, String cnpj) {
		Empresa empresa = create(null, razaoSocial, cnpj);
		return new EmpresaBuilder(empresa);
	}

	public static EmpresaBuilder newEmpresa() {
		Empresa empresa = create(null, "Empresa Test", "51463645000100");
		return new EmpresaBuilder(empresa);
	}

	private static Empresa create(Long id, String razaoSocial, String cnpj) {
		Empresa empresa = new Empresa();
		empresa.setId(id);
		empresa.setRazaoSocial(razaoSocial);
		empresa.setCnpj(cnpj);
		return empresa;
	}

	public EmpresaBuilder more(int number) {
		Empresa base = empresas.get(0);
		for (int i = 0; i < number; i++) {
			empresas.add(create(null, "Empresa Test " + i, base.getCnpj()));
		}
		return this;
	}

	public Empresa buildOne() {
		return empresas.get(0);
	}

	public List<Empresa> buildAll() {
		return empresas;
	}
}