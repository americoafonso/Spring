package com.example.algamoney.api.resource;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * Metodo para listar todos os lancamentos
     *
     * @return
     */
    @GetMapping
    public List<Lancamento> listar() {
        return lancamentoRepository.findAll();
    }

    /**
     * Metodo para salvar a entidade Lancamentos
     * @param pessoa
     * @param response
     * @return
     */
    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento pessoa, HttpServletResponse response) {
        Lancamento lancamentoSalvo = lancamentoRepository.save(pessoa);
        /** O Codigo a baixo nos retorna a url da Location que acabamos de criar Ex: 'http://localhost:8080/lancamentos/id' */
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    /***
     * Metodo para buscar a pessoa pelo codigo
     * @param codigo
     * @return
     */
    @GetMapping("/{codigo}")
    private ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<Lancamento> lancamento = lancamentoRepository.findById(codigo);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }



}
