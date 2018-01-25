package com.kw.pontointeligente.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kw.pontointeligente.api.entities.Funcionario;
import com.kw.pontointeligente.api.repositories.FuncionarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

	@MockBean
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findOne(Mockito.anyLong())).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
	}

	@Test
	public void testPersistFuncionario() {
		Funcionario funcionario = this.funcionarioService.persist(new Funcionario());
		assertNotNull(funcionario);
	}
	
	@Test
	public void testFindFuncionarioByCpf() {
		Optional<Funcionario> funcionario = this.funcionarioService.findByCpf("10283019794");
		assertTrue(funcionario.isPresent());
	}
	
	@Test
	public void testFindFuncionarioByEmail() {
		Optional<Funcionario> funcionario = this.funcionarioService.findByEmail("email@email.com");
		assertTrue(funcionario.isPresent());
	}
	
	@Test
	public void testFindFuncionarioById() {
		Optional<Funcionario> funcionario = this.funcionarioService.findById(1l);
		assertTrue(funcionario.isPresent());
	}

}
