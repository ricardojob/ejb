package org.job.dac.servicos;

import org.job.dac.Cliente;
import org.job.dac.Produto;

/**
 *
 * @author Ricardo Job
 */
public interface Carrinho {

    public void adicionarProduto(Produto produto);

    public void finalizarPedido(Cliente cliente);
}
