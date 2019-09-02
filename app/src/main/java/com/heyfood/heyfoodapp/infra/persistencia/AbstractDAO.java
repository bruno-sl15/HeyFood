package com.heyfood.heyfoodapp.infra.persistencia;

import com.heyfood.heyfoodapp.infra.HeyFoodAppRuntimeException;

import java.io.Closeable;
import java.io.IOException;

public abstract class AbstractDAO {
    protected void close(Closeable... args) throws HeyFoodAppRuntimeException {
        for (Closeable closable : args) {
            try {
                closable.close();
            } catch (IOException e) {
                throw new HeyFoodAppRuntimeException("Erro ao fechar as conexões",e);
            }
        }
    }

}
