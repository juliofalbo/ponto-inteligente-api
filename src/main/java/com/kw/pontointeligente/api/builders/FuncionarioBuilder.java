package com.kw.pontointeligente.api.builders;

import java.util.ArrayList;
import java.util.List;

import com.kw.pontointeligente.api.entities.Empresa;
import com.kw.pontointeligente.api.entities.Funcionario;
import com.kw.pontointeligente.api.enums.PerfilEnum;
import com.kw.pontointeligente.api.utils.PasswordUtils;

/**
 * Design Pattern Builder
 * 
 * Separar a construção de um objeto complexo de sua representação de modo que o
 * mesmo processo de construção possa criar diferentes representações.
 *
 */

// Implementando o padrão de projeto Builder com o objetivo de facilitar os
// testes
public class FuncionarioBuilder {

	private List<Funcionario> funcionarios = new ArrayList<>();

	private FuncionarioBuilder(Funcionario funcionario) {
		funcionarios.add(funcionario);

	}

	public static FuncionarioBuilder newFuncionario(String nome, String cpf, String email, PerfilEnum perfil,
			String senha, Empresa empresa) {
		Funcionario funcionario = create(nome, cpf, email, perfil, senha, empresa);
		return new FuncionarioBuilder(funcionario);
	}

	public static FuncionarioBuilder newFuncionario(String nome, PerfilEnum perfil) {
		Funcionario funcionario = create(nome, "10283019794", "email@email.com", perfil, "1234567",
				EmpresaBuilder.newEmpresa().buildOne());
		return new FuncionarioBuilder(funcionario);
	}

	public static FuncionarioBuilder newFuncionario(PerfilEnum perfil, Empresa empresa) {
		Funcionario funcionario = create("Funcionario Test", "10283019794", "email@email.com", perfil, "1234567",
				empresa);
		return new FuncionarioBuilder(funcionario);
	}

	public static FuncionarioBuilder newFuncionario(PerfilEnum perfil) {
		Funcionario funcionario = create("Funcionario Test", "10283019794", "email@email.com", perfil, "1234567",
				EmpresaBuilder.newEmpresa().buildOne());
		return new FuncionarioBuilder(funcionario);
	}

	public static FuncionarioBuilder newFuncionario(PerfilEnum perfil, String cpf, String email, Empresa empresa) {
		Funcionario funcionario = create("Funcionario Test", cpf, email, perfil, "1234567", empresa);
		return new FuncionarioBuilder(funcionario);
	}

	public static FuncionarioBuilder newFuncionario(PerfilEnum perfil, String cpf, String email) {
		Funcionario funcionario = create("Funcionario Test", cpf, email, perfil, "1234567",
				EmpresaBuilder.newEmpresa().buildOne());
		return new FuncionarioBuilder(funcionario);
	}

	private static Funcionario create(String nome, String cpf, String email, PerfilEnum perfil, String senha,
			Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setPerfil(perfil);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(senha));
		funcionario.setCpf(cpf);
		funcionario.setEmail(email);
		funcionario.setEmpresa(empresa);
		return funcionario;
	}

	public FuncionarioBuilder more(int number) {
		Funcionario base = funcionarios.get(0);
		for (int i = 0; i < number; i++) {
			funcionarios.add(newFuncionario("Funcionario Test " + i, base.getPerfil()).buildOne());
		}
		return this;
	}

	public Funcionario buildOne() {
		return funcionarios.get(0);
	}

	public List<Funcionario> buildAll() {
		return funcionarios;
	}
}