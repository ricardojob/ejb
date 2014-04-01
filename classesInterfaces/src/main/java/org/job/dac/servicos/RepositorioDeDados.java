package org.job.dac.servicos;

import java.util.List;

/**
 *
 * @author Ricardo Job
 */
public interface RepositorioDeDados {

    public <T> T salvar(Object obj);

    public <T> List<T> listar(Class<T> classe);
} 