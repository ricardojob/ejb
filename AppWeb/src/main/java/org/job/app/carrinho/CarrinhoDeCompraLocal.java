package org.job.app.carrinho;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import org.job.dac.Cliente;
import org.job.dac.Pedido;
import org.job.dac.Produto;
import org.job.dac.servicos.Carrinho;

/**
 *
 * @author Ricardo Job
 */
@Stateful 
public class CarrinhoDeCompraLocal  {

    @EJB
    private RepositorioPedido repositorio;

    private List<Produto> produtos = new ArrayList<Produto>();

     
    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    @Remove
    public void finalizarPedido(Cliente cliente) {
        criarPedido(cliente);
        this.produtos.clear();
    }

    private void criarPedido(Cliente cliente) {
        Pedido pedido = new Pedido(cliente, produtos);

        BigDecimal soma = new BigDecimal(BigInteger.ZERO);
        for (Produto produto : pedido.getItensVendas()) {
            soma = soma.add(produto.getPreco());
        }        
        pedido.setTotal(soma);
        
        repositorio.salvarPedido(pedido);
    }
}
