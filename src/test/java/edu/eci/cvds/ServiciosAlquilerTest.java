package edu.eci.cvds;

import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Assert;
import java.util.Date;
import org.springframework.transaction.annotation.Transactional;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner; 
import org.springframework.test.context.ContextConfiguration;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;
	@Inject
    private ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
    }

    @Test
    public void emptyDB() {
        for(int i = 0; i < 100; i += 10) {
            boolean r = false;
            try {
                Cliente cliente = serviciosAlquiler.consultarCliente(15422412);
            } catch(ExcepcionServiciosAlquiler e) {
                r = true;
            } catch(IndexOutOfBoundsException e) {
                r = true;
            }
            // Validate no Client was found;
            Assert.assertTrue(r);
        };
    }
	
	@Test
	public void consultarCliente(){
		boolean r = false;
		try{
			Cliente c = serviciosAlquiler.consultarCliente((long) 10000);
			if (c.getDocumento() == (long) 10000){
				r = true;
			}
		}
		catch(ExcepcionServiciosAlquiler e) {
			r=false;
		}
		catch(IndexOutOfBoundsException e) {
            r = false;
        }
		Assert.assertTrue(r);
	}
	
	@Test
	public void consultarItem(){
		boolean r = false;
		try{
			Item i = serviciosAlquiler.consultarItem(100);
			if (i.getId() == 100){
				r = true;
			}
		}
		catch(ExcepcionServiciosAlquiler e) {
			r=false;
		}
		catch(IndexOutOfBoundsException e) {
            r = false;
        }
		Assert.assertTrue(r);
	}
	
	
	@Test
	public void rentarItem(){
		boolean r = false;
		try{
			Date fechaHoy = new Date();
			java.sql.Date sDate = new java.sql.Date(fechaHoy.getTime());
			Item item = serviciosAlquiler.consultarItem(100);
			List<ItemRentado> li = serviciosAlquiler.consultarItemsCliente((long) 10000);
			int val = li.size();
			serviciosAlquiler.registrarAlquilerCliente(sDate, 10000, item, 10);
			li = serviciosAlquiler.consultarItemsCliente((long) 10000);
			if (li.size() == val + 1){
				r = true;
			}
		}
		catch(ExcepcionServiciosAlquiler e) {
			r=false;
		}
		catch(IndexOutOfBoundsException e) {
            r = false;
        }
		Assert.assertTrue(r);
	}	
	
	@Test
	public void cambiarCliente(){
		boolean r = false;
		try{
			boolean valor = serviciosAlquiler.consultarCliente((long) 10000).isVetado();
			if (valor){
				serviciosAlquiler.vetarCliente((long) 10000, true);
				r = true;
			}
			else{
				serviciosAlquiler.vetarCliente((long) 10000, false);
				r = false;
			}
			if (r == valor){
				r = true;
			}
			else{
				r = false;
			}
		}catch(ExcepcionServiciosAlquiler e) {
			r=false;
		}
		catch(IndexOutOfBoundsException e) {
            r = false;
        }
		Assert.assertTrue(r);		
	}	
	
	@Test
	public void consultarTipoItems(){
		boolean r = true;
		try{
			List<TipoItem> tis = serviciosAlquiler.consultarTiposItem();
			TipoItem ti;
			for (int i = 0; i < tis.size(); i++){
				ti = serviciosAlquiler.consultarTipoItem(tis.get(i).getID());
				System.out.println(ti);
			}
		}catch(ExcepcionServiciosAlquiler e) {
			r=false;
		}
		catch(IndexOutOfBoundsException e) {
            r = false;
        }
		Assert.assertTrue(r);
	}
	
	@Test
	public void deberiaSer0CuandoSon0Dias(){
		boolean r = false;
		try{
			long val = serviciosAlquiler.consultarCostoAlquiler(100, 0);
			if (val == 0){
				r = true;
			}
		}catch(ExcepcionServiciosAlquiler e) {
			r=false;
		}
		catch(IndexOutOfBoundsException e) {
            r = false;
        }
		Assert.assertTrue(r);
	}
}
