package com.kw.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kw.pontointeligente.api.builders.CadastroPJDtoBuilder;
import com.kw.pontointeligente.api.builders.EmpresaBuilder;
import com.kw.pontointeligente.api.builders.FuncionarioBuilder;
import com.kw.pontointeligente.api.dtos.CadastroPJDto;
import com.kw.pontointeligente.api.entities.Empresa;
import com.kw.pontointeligente.api.entities.Funcionario;
import com.kw.pontointeligente.api.response.Response;
import com.kw.pontointeligente.api.services.EmpresaService;
import com.kw.pontointeligente.api.services.FuncionarioService;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando PJ: {}", cadastroPJDto.getNome());

		Response<CadastroPJDto> response = new Response<CadastroPJDto>();

		validarDadosExistentes(cadastroPJDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);

		if (result.hasErrors()) {
			log.error("Erro ao validar os dados de cadastro de PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.empresaService.persist(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persist(funcionario);

		response.setData(this.converterCadastroPJDTo(funcionario));

		return ResponseEntity.ok(response);

	}

	private void validarDadosExistentes(CadastroPJDto cadastroPJDto, BindingResult result) {

		this.empresaService.findByCnpj(cadastroPJDto.getCnpj())
				.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existe")));

		this.funcionarioService.findByCpf(cadastroPJDto.getCpf())
				.ifPresent(emp -> result.addError(new ObjectError("funcionario", "CPF já existe")));

		this.funcionarioService.findByEmail(cadastroPJDto.getEmail())
				.ifPresent(emp -> result.addError(new ObjectError("funcionario", "Email já existe")));

	}

	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
		return EmpresaBuilder.newEmpresa(cadastroPJDto.getRazaoSocial(), cadastroPJDto.getCnpj()).buildOne();
	}

	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) {

		return FuncionarioBuilder.newFuncionario(cadastroPJDto).buildOne();
	}

	private CadastroPJDto converterCadastroPJDTo(Funcionario funcionario) {
		return CadastroPJDtoBuilder.newPJDto(funcionario).buildOne();
	}

}
