package org.job.app.beans;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.job.dac.Cliente;
import org.job.dac.Produto;

/**
 *
 * @author Ricardo Job
 */
@Singleton
@Startup
public class ProdutoEmDestaque {

    @EJB
    private RepositorioDeDadosLocal repositorio;

    private Produto produtoDestaque;

    @Schedule(second = "*/30", minute = "*", hour = "*")
    public void trocaProdutoDestaque() {
        Random gerador = new Random();
        List<Produto> produtos = this.repositorio.listar(Produto.class);
        int i = gerador.nextInt(produtos.size());
        this.produtoDestaque = produtos.get(i);
        System.out.println("Produto em Destaque: " + produtoDestaque.getNome());
    }

    public void setProdutoDestaque(Produto produtoDestaque) {
        this.produtoDestaque = produtoDestaque;
    }

    @Lock(LockType.READ)
    public Produto getProdutoDestaque() {
        return produtoDestaque;
    }

    @PostConstruct
    public void initVitrine() {
//        repositorio.salvar(new Produto("Livro", new BigDecimal("190.50")));
////        repositorio.salvar(new Produto("Celular", new BigDecimal("350.50")));
//        repositorio.salvar(new Produto("Notebook", new BigDecimal("1250.75")));

//        repositorio.salvar(new Cliente("Ricardo Job", "123456"));
//        repositorio.salvar(new Cliente("Ana Maria", "1254125"));
//        repositorio.salvar(new Cliente("João Carlos", "965874"));
    }
}
