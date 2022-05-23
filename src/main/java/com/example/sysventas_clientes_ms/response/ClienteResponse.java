package com.example.sysventas_clientes_ms.response;

import com.example.sysventas_clientes_ms.entity.Cliente;

import lombok.Data;

@Data
public class ClienteResponse {
	private Cliente cliente;
	private String result;
	private String error;
	private String message;
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ClienteResponse(Cliente cliente, String result, String error, String message) {
		super();
		this.cliente = cliente;
		this.result = result;
		this.error = error;
		this.message = message;
	}
}
