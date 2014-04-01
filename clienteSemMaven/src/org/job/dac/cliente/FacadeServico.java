/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.job.dac.cliente;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.job.dac.Cliente;
import org.job.dac.Produto;
import org.job.dac.servicos.Carrinho;
import org.job.dac.servicos.RepositorioDeDados;

/**
 *
 * @author Ricardo Job
 */
public class FacadeServico {

    private static final String REPOSITORIO = "java:global/AppWeb/RepositorioDeDadosRemoto!org.job.dac.servicos.RepositorioDeDados";
    private static final String CARRINHO = "java:global/AppWeb/CarrinhoDeCompra!org.job.dac.servicos.Carrinho";

    private RepositorioDeDados dao = lookup(REPOSITORIO, RepositorioDeDados.class);

    public List<Cliente> clientes() {
        return dao.listar(Cliente.class);
    }

    public List<Produto> produtos() {
        return dao.listar(Produto.class);
    }

    public void realizarPedido(Cliente cliente, List<Produto> lista) {
        Carrinho carrinho = lookup(CARRINHO, Carrinho.class);
        for (Produto produto : lista) {
            carrinho.adicionarProduto(produto);
        }
        carrinho.finalizarPedido(cliente);
    }

    private <T> T lookup(String recurso, Class<T> tipo) {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

            InitialContext context = new InitialContext(props);
            return (T) context.lookup(recurso);
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            ne.printStackTrace();
            throw new RuntimeException(ne);
        }
    }
//    public List<Produto> produtos() {
//        List<Produto> produtos = new ArrayList<Produto>();
//        produtos.add(new Produto("Carro", BigDecimal.ZERO));
//        produtos.add(new Produto("Livro", BigDecimal.TEN));
//        produtos.add(new Produto("Note", BigDecimal.ONE));
//        return produtos;
//    }
//     public List<Cliente> clientes() {
//        List<Cliente> clientes = new ArrayList<>();
//        clientes.add(new Cliente("Kiko", "12365"));
//        clientes.add(new Cliente("Neto", "1234"));
//        return clientes;
//     
//    }

}
