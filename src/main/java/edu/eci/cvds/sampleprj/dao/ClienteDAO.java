package edu.eci.cvds.sampleprj.dao;

import edu.eci.cvds.samples.entities.Cliente;
import java.util.*;

public interface ClienteDAO {

   public void save(Cliente cl) throws PersistenceException;

   public Cliente load(int id) throws PersistenceException;
   
   public void rentarPelicula(int id, int idit, Date fechainicio, Date fechafin) throws PersistenceException;

   public List<Cliente> consultarClientes() throws PersistenceException;
   
   public void cambiarVetado(int id, int val);
}