package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import java.util.*;

public interface ClienteDAO {

   public void save(Cliente cl) throws PersistenceException;

   public Cliente load(long id) throws PersistenceException;
   
   public void rentarPelicula(long id, int idit, Date fechainicio, Date fechafin) throws PersistenceException;

   public List<Cliente> consultarClientes() throws PersistenceException;
   
   public void cambiarVetado(long id, int val) throws PersistenceException;
   
   public ItemRentado loadRentado(int id) throws PersistenceException;
}