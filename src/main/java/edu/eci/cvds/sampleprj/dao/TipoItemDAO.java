package edu.eci.cvds.sampleprj.dao;
import java.util.*;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.TipoItem;

public interface TipoItemDAO {

   public List<TipoItem> consultarTipos() throws PersistenceException;

   public TipoItem consultarTipo(int id) throws PersistenceException;
   
}