package com.kw.pontointeligente.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.kw.pontointeligente.api.builders.EmpresaBuilder;
import com.kw.pontointeligente.api.services.EmpresaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmpresaService empresaService;

	private static final String URL = "/api/empresas/cnpj/";
	private static final Long ID = 1L;
	private static final String CNPJ = "10080325000185";
	private static final String RAZAO_SOCIAL = "KW TI LTDA";

	@Test
	@WithMockUser
	public void testFindByInvalidCnpj() throws Exception {
		BDDMockito.given(this.empresaService.findByCnpj(Mockito.anyString())).willReturn(Optional.empty());

		mvc.perform(MockMvcRequestBuilders.get(URL + CNPJ).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ " + CNPJ));
	}

	@Test
	@WithMockUser
	public void testFindByValidCnpj() throws Exception {
		BDDMockito.given(this.empresaService.findByCnpj(Mockito.anyString()))
				.willReturn(Optional.of(EmpresaBuilder.newEmpresa(ID, RAZAO_SOCIAL, CNPJ).buildOne()));

		mvc.perform(MockMvcRequestBuilders.get(URL + CNPJ).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.razaoSocial").value(equalTo(RAZAO_SOCIAL)))
				.andExpect(jsonPath("$.data.cnpj").value(equalTo(CNPJ)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

}
