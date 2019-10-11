package edu.eci.cvds.view;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import org.primefaces.event.SelectEvent;
import javax.faces.context.FacesContext; 
import javax.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;

@SuppressWarnings("deprecation")
@ManagedBean(name = "clienteBean")
@RequestScoped
public class ClienteBean extends BasePageBean {
	@ManagedProperty(value = "#{param.nombre}")
	private String nombre;
	@ManagedProperty(value = "#{param.documento}")
	private long documento;
	@ManagedProperty(value = "#{param.telefono}")
    private String telefono;
	@ManagedProperty(value = "#{param.direccion}")
    private String direccion;
	@ManagedProperty(value = "#{param.email}")
    private String email;
	
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
			return serviciosAlquiler.consultarItemsCliente(documento);
		} catch (ExcepcionServiciosAlquiler ex) {
			throw ex;
		}
	}

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		System.out.println(nombre);
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
			FacesContext.getCurrentInstance().getExternalContext().redirect("registroalquiler.xhtml");
		}catch (Exception e){
			e.printStackTrace();
		}
    }
}
