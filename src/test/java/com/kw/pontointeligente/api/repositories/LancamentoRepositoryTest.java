package com.kw.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kw.pontointeligente.api.builders.EmpresaBuilder;
import com.kw.pontointeligente.api.builders.FuncionarioBuilder;
import com.kw.pontointeligente.api.builders.LancamentoBuilder;
import com.kw.pontointeligente.api.entities.Empresa;
import com.kw.pontointeligente.api.entities.Funcionario;
import com.kw.pontointeligente.api.entities.Lancamento;
import com.kw.pontointeligente.api.enums.PerfilEnum;
import com.kw.pontointeligente.api.enums.TipoEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private LancamentoRepository lancamentoRepository;

	private Long funcionarioId;

	@Before
	public void setUp() throws Exception {

		Empresa empresa = this.empresaRepository.save(EmpresaBuilder.newEmpresa().buildOne());
		Funcionario funcionario = this.funcionarioRepository
				.save(FuncionarioBuilder.newFuncionario(PerfilEnum.ROLE_USUARIO, empresa).buildOne());
		this.funcionarioId = funcionario.getId();

		this.lancamentoRepository
				.save(LancamentoBuilder.newLancamento(TipoEnum.INICIO_TRABALHO, funcionario).more(2).buildAll());
	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testFindByFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId);
		assertEquals(3, lancamentos.size());
	}

	@Test
	public void testFindByFuncionarioIdPageable() {
		PageRequest page = new PageRequest(0, 10);
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(this.funcionarioId, page);
		assertEquals(3, lancamentos.size());
	}

}
