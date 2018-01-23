package com.kw.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kw.pontointeligente.api.builders.EmpresaBuilder;
import com.kw.pontointeligente.api.builders.FuncionarioBuilder;
import com.kw.pontointeligente.api.entities.Empresa;
import com.kw.pontointeligente.api.entities.Funcionario;
import com.kw.pontointeligente.api.enums.PerfilEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	private String CPF = "10283019794";
	private String EMAIL = "email@email.com";

	@Before
	public void setUp() throws Exception {
		
		Empresa empresa = this.empresaRepository.save(EmpresaBuilder.newEmpresa().buildOne());
		this.funcionarioRepository
				.save(FuncionarioBuilder.newFuncionario(PerfilEnum.ROLE_USUARIO, CPF, EMAIL, empresa).buildOne());
	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testFindByCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
		assertEquals(CPF, funcionario.getCpf());
	}

	@Test
	public void testFindByEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
		assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test
	public void testFindByCpfOrEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		assertNotNull(funcionario);
	}

	@Test
	public void testFindByCpfOrEmailWithInvalidCpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("11111111111", EMAIL);
		assertNotNull(funcionario);
	}

	@Test
	public void testFindByCpfOrEmailWithInvalidEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com.br");
		assertNotNull(funcionario);
	}

}
