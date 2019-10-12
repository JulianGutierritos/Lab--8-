package edu.eci.cvds.view;
import java.util.List;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import javax.faces.context.FacesContext; 
import javax.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import java.util.Date;

@SuppressWarnings("deprecation")
@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean extends BasePageBean {
	private String nombre;
	private long documento;
    private String telefono;
    private String direccion;
    private String email;
    private int dias;
    private int codigo;
	
	private static final long serialVersionUID = 3594009161252782831L;

	@Inject
	private ServiciosAlquiler serviciosAlquiler;

	public List<Cliente> getData() throws ExcepcionServiciosAlquiler{
		try {
			List<Cliente> l = serviciosAlquiler.consultarClientes();
			if (documento != 0){
				for (int i = 0; i < l.size(); i++){
					if (l.get(i).getDocumento() == this.documento){
						Cliente c = l.get(i);
						l.remove(i);
						l.add(0, c);
						break;
					}
				}
			}
			return l;
		} catch (ExcepcionServiciosAlquiler ex) {
			throw ex;
		}
	}
	
	public void insertarCliente() throws ExcepcionServiciosAlquiler{
		try{
			Cliente c = new Cliente(this.nombre, this.documento, this.telefono, this.direccion, this.email, false, null);
			serviciosAlquiler.registrarCliente(c);
		} catch (ExcepcionServiciosAlquiler ex) {
			throw ex;
		}
	}
	
	public Cliente getCliente() throws ExcepcionServiciosAlquiler{
		try{
			return serviciosAlquiler.consultarCliente(documento);
		} catch (ExcepcionServiciosAlquiler ex) {
			throw ex;
		}
	}
	
	public List<ItemRentado> getRentados() throws ExcepcionServiciosAlquiler{
		try{
			List<ItemRentado> items = serviciosAlquiler.consultarItemsCliente(documento);
			List<ItemRentado> itemsActivos = new ArrayList<ItemRentado>(); 
			for (int i=0; i < items.size() ; i++){
				ItemRentado it = items.get(i);
				Date fechaHoy = new Date();
				fechaHoy.getDay();
				if (fechaHoy.before(it.getFechafinrenta())){
					java.sql.Date sDate = new java.sql.Date(fechaHoy.getTime());
					serviciosAlquiler.consultarMultaAlquiler(it.getId(), sDate);
					itemsActivos.add(it);
				}
			}
			return itemsActivos;
		} catch (ExcepcionServiciosAlquiler ex) {
			throw ex;
		}
		
	}
	
	public void rentar() throws ExcepcionServiciosAlquiler{
		try{
			Date fechaHoy = new Date();
			Item it = serviciosAlquiler.consultarItem(codigo);
			java.sql.Date fecha = new java.sql.Date(fechaHoy.getTime());
			serviciosAlquiler.registrarAlquilerCliente(fecha, documento, it, dias);
		} catch (ExcepcionServiciosAlquiler ex) {
			throw ex;
		}
	}
	
	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}
	
	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public long getDocumento() {
		return documento;
	}

	public void setDocumento(long documento) {
		this.documento = documento;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void onRowSelect(SelectEvent event) {
		try{
			documento = ((Cliente) event.getObject()).getDocumento();
			FacesContext.getCurrentInstance().getExternalContext().redirect("registroalquiler.xhtml?documento=" + documento);
		}catch (Exception e){
			e.printStackTrace();
		}
    }
	
	public void reiniciar(){
		this.documento=0;
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("registrocliente.xhtml");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
