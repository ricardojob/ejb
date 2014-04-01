/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.job.app.appweb;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.job.dac.Cliente;

/**
 *
 * @author Ricardo Job
 */
@FacesConverter(forClass = Cliente.class, value = "clienteConverter")
public class ClienteConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value != null && !value.isEmpty()) {
            return (Cliente) component.getAttributes().get(value);
        }
        return null;

    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Cliente) {
            Cliente cliente = (Cliente) value;
            if (cliente != null && cliente instanceof Cliente && cliente.getId() != null) {
                uiComponent.getAttributes().put(cliente.getId().toString(), cliente);
                return cliente.getId().toString();
            }
        }
        return "";
    }

}
