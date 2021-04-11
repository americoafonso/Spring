package com.example.algamoney.api.repository.projection;

public class ResumoCategoria {
	
	private Long codigo;
	private String categoria;
	
	public ResumoCategoria(Long codigo, String categoria) {
		super();
		this.codigo = codigo;
		this.categoria = categoria;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
