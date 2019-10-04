package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemMapper;
import edu.eci.cvds.samples.entities.Cliente;
import java.sql.SQLException;
import java.util.*;

public class MyBATISClienteDAO implements ClienteDAO{

  @Inject
  private ClienteMapper clienteMapper;    

  @Override
  public void save(Cliente cl) throws PersistenceException{
  try{
      clienteMapper.agregarCliente(cl);
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException("Error al registrar el cliente "+cl.toString(),e);
  }        

  }

  @Override
  public Cliente load(int id) throws PersistenceException {
  try{
      return clienteMapper.consultarCliente(id);
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException("Error al consultar el cliente "+id,e);
  }
  }

  public void rentarPelicula(int id, int idit, Date fechainicio, Date fechafin) throws PersistenceException{
  try{
      clienteMapper.agregarItemRentadoACliente(id, idit, fechainicio, fechafin);
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException("Error al rentar pelicula "+idit,e);
  }
  
  }
  
  public List<Cliente> consultarClientes() throws PersistenceException{
  try{
      return clienteMapper.consultarClientes();
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException("Error al consultar clientes",e);
  }
  
  }

  public void cambiarVetado(int id, int val) throws PersistenceException{
  try{
      clienteMapper.setCliente(id, val);
  }
  catch(org.apache.ibatis.exceptions.PersistenceException e){
      throw new PersistenceException("Error al consultar el estado del cliente "+id,e);
  }
  
  }
}