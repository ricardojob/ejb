package org.job.dac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Ricardo Job
 */
@Entity
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private StatusPedido status;
    private BigDecimal total;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Produto> itensVendas;

    public Pedido() {
        this(null);
    }

    public Pedido(Cliente cliente) {
        this(cliente, new ArrayList<Produto>());
    }

    public Pedido(Cliente cliente, List<Produto> itensVendas) {
        this.cliente = cliente;
        this.itensVendas = itensVendas;
        this.status = StatusPedido.ANDAMENTO;
    }

    public BigDecimal getTotal() {
         // processaProdutos();
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getItensVendas() {
        return itensVendas;
    }

    public void setItensVendas(List<Produto> itensVendas) {
        this.itensVendas = itensVendas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Venda[ id=" + id + " ]";
    }

    private void processaProdutos() {
        for (Produto produto : itensVendas) {
            total = total.add(produto.getPreco());
        }
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

}
