package com.example.algamoney.api.repository.lancamento;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;

import javax.persistence.criteria.Root;
import java.util.List;

public interface LancamentoRepositoryQuery {
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Root<Lancamento> root);

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
