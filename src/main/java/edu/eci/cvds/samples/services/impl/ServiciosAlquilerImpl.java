package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDAO;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.util.List;
import java.util.Calendar;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

   @Inject
   private ItemDAO itemDAO;
   @Inject
   private ClienteDAO clienteDAO;
   @Inject
   private TipoItemDAO tipoItemDAO;
   private ServiciosAlquilerItemsStub st = new ServiciosAlquilerItemsStub();
   @Override
   public int valorMultaRetrasoxDia(int itemId){
	   return st.valorMultaRetrasoxDia(itemId);
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       try {
           return clienteDAO.load(docu);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el cliente "+docu,ex);
       }
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
       try {
           Cliente c = consultarCliente(idcliente);
		   return c.getRentados();
       } catch (ExcepcionServiciosAlquiler ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los items del cliente "+idcliente,ex);
       }
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
       try {
           return clienteDAO.consultarClientes();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar clientes ",ex);
       }
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler {
	    try {
           return itemDAO.loadLibres();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los items libres",ex);
       }
   }

   @Override
   public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
		try{
			ItemRentado ir = clienteDAO.loadRentado(iditem);
		   int valor = (int) ir.getItem().getTarifaxDia();
		   Date fechaDev = fechaDevolucion;
		   Date fechaFin = ir.getFechafinrenta();
		   int res = valor * ((int) ((fechaDev.getTime()-fechaFin.getTime())/86400000)); 
		   if (res < 0){
			   res = 0;
		   }
		   return res;
		} catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al calcular multa"+ iditem,ex);
       }   
   }

   @Override
   public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return tipoItemDAO.consultarTipo(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el tipo "+id,ex);
       }
   }

   @Override
   public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
       try {
           return tipoItemDAO.consultarTipos();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los tipos ",ex);
       }
   }

   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
       try {
		   Calendar cal = Calendar.getInstance();
		   cal.setTime(date);
		   cal.add(Calendar.DAY_OF_YEAR, numdias); 
           clienteDAO.rentarPelicula(docu, item.getId(), date, cal.getTime());
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al registrar renta",ex);
       }
   }

   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
       try {
           clienteDAO.save(c);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al registrar cliente",ex);
       }
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
       try {
		int resultado = numdias * (int) consultarItem(iditem).getTarifaxDia();
		return resultado;
	   } catch (ExcepcionServiciosAlquiler ex) {
           throw new ExcepcionServiciosAlquiler("Error al calcular costo del item " + iditem ,ex);
       }
   }

   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       try {
           itemDAO.actualizarTarifa(id, tarifa);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al actualizar tarifa del item "+id,ex);
       }
   }
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
       try {
           itemDAO.save(i);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al guardar item",ex);
       }
   }

   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
       try {
		   int val = 0;
		   if (estado){
				val = 1;
		   }
           clienteDAO.cambiarVetado(docu, val);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al vetar cliente " + docu ,ex);
       }
   }
}