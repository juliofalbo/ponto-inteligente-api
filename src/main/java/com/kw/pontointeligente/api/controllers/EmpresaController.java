package com.kw.pontointeligente.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kw.pontointeligente.api.builders.EmpresaDtoBuilder;
import com.kw.pontointeligente.api.dtos.EmpresaDto;
import com.kw.pontointeligente.api.entities.Empresa;
import com.kw.pontointeligente.api.response.Response;
import com.kw.pontointeligente.api.services.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);

	@Autowired
	private EmpresaService empresaService;

	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> findByCnpj(@PathVariable("cnpj") String cnpj) {
		log.info("Cadastrando a Empresa: {}", cnpj);

		Response<EmpresaDto> response = new Response<EmpresaDto>();

		Optional<Empresa> empresa = this.empresaService.findByCnpj(cnpj);

		if (!empresa.isPresent()) {
			log.error("Empresa não encontrada para o CNPJ: {}", cnpj);
			response.getErrors().add("Empresa não encontrada para o CNPJ " + cnpj);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(this.converterEmpresaDto(empresa.get()));

		return ResponseEntity.ok(response);

	}

	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		return EmpresaDtoBuilder.newEmpresaDto(empresa).buildOne();
	}

}
