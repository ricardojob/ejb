/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clientesemmaven;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.job.dac.Cliente;
import org.job.dac.Pedido;
import org.job.dac.Produto;
import org.job.dac.servicos.Carrinho;
import org.job.dac.servicos.RepositorioDeDados;

/**
 *
 * @author Ricardo Job
 */
public class TestandoPrimeiroExemplo {

    private static final String REPOSITORIO = "java:global/segundoTeste-ear/segundoTeste-ejb-1.0-SNAPSHOT/RepositorioDeDadosRemoto!org.job.dac.servicos.RepositorioDeDados";
    private static final String CARRINHO = "java:global/segundoTeste-ear/segundoTeste-ejb-1.0-SNAPSHOT/CarrinhoDeCompra!org.job.dac.servicos.Carrinho";

    public static void main(String[] args) {
        new TestandoPrimeiroExemplo().init();
    }

    private void init() {
        RepositorioDeDados dao = lookup(REPOSITORIO, RepositorioDeDados.class);

        Cliente cliente = new Cliente("Neto", "12358");

        Produto prod = new Produto("Note", new BigDecimal("690.50"));
        cliente = dao.salvar(cliente);
        prod = dao.salvar(prod);

        System.out.println("-----Clientes----");
        for (Cliente cli : dao.listar(Cliente.class)) {
            System.out.println("Id: " + cli.getId() + " Nome: " + cli.getNome());
        }
        
        Carrinho carrinho = lookup(CARRINHO, Carrinho.class);
        System.out.println("-----Produtos----");
        
        for (Produto produto : dao.listar(Produto.class)) {
            System.out.println("Id: " + produto.getId() + " Nome: " + produto.getNome());
            carrinho.adicionarProduto(produto);
        }

        
        //   List<Produto> lista = dao.listar(Produto.class);
        //for (Produto produto : dao.listar(Produto.class)) {
            
        //}

        // carrinho.adicionarProduto(produto);
       // carrinho.finalizarPedido(cliente);
        System.out.println("-----Pedidos----");
        for (Pedido pedido : dao.listar(Pedido.class)) {
            System.out.println("Id: " + pedido.getId() + " Total: " + pedido.getTotal());
        }

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
}
