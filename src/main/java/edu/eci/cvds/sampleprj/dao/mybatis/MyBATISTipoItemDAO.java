package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;
import java.sql.SQLException;
import java.util.*;

public class MyBATISTipoItemDAO implements ItemDAO{

  @Inject
  private TipoItemMapper tipoItemMapper;    

  @Override
  public List<TipoItem> consultarTipos() throws PersistenceException{
  try{
      tipoItemMapper.getTiposItems();
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException("Error al listar los tipos de items",e);
  }        

  }

  @Override
  public TipoItem consultarTipo(int id) throws PersistenceException {
  try{
      return tipoItemMapper.getTipoItem(id);
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException("Error al consultar el tipo de item "+id,e);
  }
  }
  

  }