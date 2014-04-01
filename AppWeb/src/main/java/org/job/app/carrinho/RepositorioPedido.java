package org.job.app.carrinho;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import org.job.app.beans.RepositorioDeDadosLocal;
import org.job.dac.Pedido;

/**
 *
 * @author Ricardo Job
 */
@Stateless
public class RepositorioPedido {

    @Resource(mappedName = "jms/pedidos")
    private Queue pedidos;
    @Resource(mappedName = "jms/pedidosFactory")
    private ConnectionFactory pedidosFactory;

    @EJB
    private RepositorioDeDadosLocal repositorio;

    public void salvarPedido(Pedido pedido) {
        repositorio.salvar(pedido);
        sendJMSMessageToPedidos(pedido);
    }

    private void sendJMSMessageToPedidos(Pedido messageData) {
        try {
            Connection connection = pedidosFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(pedidos);

            ObjectMessage message = session.createObjectMessage();
            message.setObject(messageData);
            messageProducer.send(message);

            session.close();
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(RepositorioPedido.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
