package com.igortullio.cursomc.resources;

import com.igortullio.cursomc.domain.Categoria;
import com.igortullio.cursomc.domain.Produto;
import com.igortullio.cursomc.dto.CategoriaDTO;
import com.igortullio.cursomc.dto.ProdutoDTO;
import com.igortullio.cursomc.resources.utils.URL;
import com.igortullio.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id){
        Produto obj = produtoService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction){
        String nomeDecoded = URL.decodeParam(nome);
        List<Integer> ids = URL.decodeIntList(categorias);
        Page<Produto> produtos = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> produtoDTO = produtos.map(produto -> new ProdutoDTO(produto));
        return ResponseEntity.ok().body(produtoDTO);
    }

}
