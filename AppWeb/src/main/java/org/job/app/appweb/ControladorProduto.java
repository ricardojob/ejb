package org.job.app.appweb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.job.app.beans.RepositorioDeDadosLocal;
import org.job.app.carrinho.CarrinhoDeCompraLocal;
import org.job.dac.Cliente;
import org.job.dac.Produto;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author Ricardo Job
 */
@ManagedBean
@ViewScoped
public class ControladorProduto implements Serializable {

    @EJB
    private RepositorioDeDadosLocal repositorio;

    @EJB
    private CarrinhoDeCompraLocal carrinho;

    private List<Produto> listaDeProduto = new ArrayList<Produto>();
    private List<Produto> produtosNoCarrinho = new ArrayList<Produto>();

    private Produto produtoSelecionado = new Produto();
    private Cliente clienteSelecionado = new Cliente();

    public ControladorProduto() {

    }

    public String comprar() {
        try {
            carrinho.finalizarPedido(clienteSelecionado);
            produtosNoCarrinho = new ArrayList<Produto>();
            return "/Confirmacao.xhtml";
        } catch (Exception e) {
            return "/Erro.xhtml";
        }

//        System.out.println("Carrinho: " + carrinho);
//        return logout();
    }

    public void remover(Produto produto) {
        if (produtosNoCarrinho.contains(produto)) {
            produtosNoCarrinho.remove(produto);
        }
    }

    public List<Cliente> getClientes() {
        return repositorio.listar(Cliente.class);
    }

    public void onCarDrop(DragDropEvent ddEvent) {
        Produto produtoDrop = ((Produto) ddEvent.getData());
        produtosNoCarrinho.add(produtoDrop);
        carrinho.adicionarProduto(produtoDrop);
    }

    public List<Produto> getListaDeProduto() {
        if (listaDeProduto.isEmpty()) {
            listaDeProduto = repositorio.listar(Produto.class);
        }
        return listaDeProduto;
    }

    public List<Produto> getProdutosNoCarrinho() {
        return produtosNoCarrinho;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public List<SelectItem> getSelectClientes() {
        List<SelectItem> listaSelect = new ArrayList<SelectItem>();

        for (Cliente cli : this.getClientes()) {
            listaSelect.add(new SelectItem(cli, cli.getNome()));;
        }

        return listaSelect;

    }

    public String logout() {
        //Redireciona o usuário para tela de login efetuando o logout.  
        String loginPage = "/Confirmacao.xhtml";
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        HttpSession session = (HttpSession) context.getSession(false);
        session.invalidate();
        try {
            context.redirect(request.getContextPath() + loginPage);
        } catch (IOException e) {
            //logger.error("Erro ao tentar redirecionar para página solicitada ao efetuar Logout: " + e.toString());  
        }

        return "Erro.xhtml";
    }

}
