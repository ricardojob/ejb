/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.job.app.beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.job.dac.Pedido;
import org.job.dac.Produto;

/**
 *
 * @author Ricardo Job
 */
@MessageDriven(
        mappedName = "jms/pedidos", activationConfig = {
            @ActivationConfigProperty(propertyName = "destinationType",
                    propertyValue = "javax.jms.Queue")
        })
public class ProcessaPedidoAndamento implements MessageListener {

    @Resource
    private MessageDrivenContext session;
    @EJB
    private RepositorioDeDadosLocal repositorio;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objeto = (ObjectMessage) message;
            Pedido pedido = (Pedido) objeto.getObject();

            System.out.println("Consumindo o Pedido: " + pedido.getId() + " - " + pedido.getStatus().name());
            repositorio.atualizar(pedido);
            for (Produto produto : pedido.getItensVendas()) {
                System.out.println("Id: " + produto.getId() + " Nome: " + produto.getNome());
            }

            System.out.println("Mensagem consumida com sucesso!!");
        } catch (JMSException ex) {
            Logger.getLogger(ProcessaPedidoAndamento.class.getName()).log(Level.SEVERE, null, ex);
            session.setRollbackOnly();
        }
    }

}
