package com.example.sysventas_clientes_ms.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sysventas_clientes_ms.entity.Cliente;
import com.example.sysventas_clientes_ms.model.ClienteRepository;
import com.example.sysventas_clientes_ms.response.ClienteResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {
	
	private final static Logger logger = Logger.getLogger("ClienteRestController");
	
	@Autowired
	MessageSource messageSource;

	@Autowired
	ClienteRepository clienteRepository;
	
	@GetMapping("/list")
	public List<Cliente> listClientes() {		
		
		return clienteRepository.findClientesOrderByName();
	}
	
	@GetMapping
	public ResponseEntity<Optional<Cliente>> getCliente(@RequestParam(name = "id") Long id) {		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if(!cliente.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cliente);
	}
	
	@Transactional
	@PostMapping("/add")
	public ClienteResponse saveCliente(@RequestBody @Valid Cliente cliente, BindingResult bindingResult) {		
				
		loggerInfo(cliente, "Guardar cliente.", "Web client.\n");
		
		ClienteResponse clienteResponse = null;
		
		try {
			
			if (bindingResult.hasErrors()) {
				clienteResponse = new ClienteResponse(null, "Not OK", "true", "Hay errores en los campos del cliente");					
				loggerMessage(clienteResponse.getMessage());					
				return clienteResponse;
			}
			
			Optional<Cliente> optCliente = clienteRepository.findClienteByCode(cliente.getCedula());
			
			if (optCliente.isPresent()) {
				
				clienteResponse = new ClienteResponse(null, "Not OK", "true", "Ya existe un cliente con esa cédula");
				
			}else {
				
				clienteRepository.save(cliente);
				clienteResponse = new ClienteResponse(cliente, "OK", "false", "Cliente registrado correctamente");
			}							
		
		} catch (Exception ex) {
			clienteResponse = new ClienteResponse(null, "Not OK", "true", ex.getMessage());	
		}
		
		loggerMessage(clienteResponse.getMessage());			
		
		return clienteResponse;
	}
	
	public void loggerInfo(Object obj, String action, String client) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String employeeJson = objectMapper.writeValueAsString(obj);
			System.out.println("\n");
			logger.info("########## EmployeeRestController ##########");
			logger.info(employeeJson);			
			logger.info(action);
			logger.info(client);			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void loggerMessage(String message) {
		
		System.out.println("\n");
		logger.info("Message: " + message + "\n");
	}
	
}
