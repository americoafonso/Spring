package com.example.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.example.algamoney.api.repository.filter.LancamentoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.service.LancamentoService;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private MessageSource messageSource;

    @GetMapping
    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
        return lancamentoRepository.filtrar(lancamentoFilter);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Optional<Lancamento>> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    /** Metodo para remover lancamento*/
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        this.lancamentoRepository.deleteById(codigo);
    }

    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @ExceptionHandler({ PessoaInexistenteOuInativaException.class })
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }

}