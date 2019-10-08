package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
/**
 *
 * @author 2106913
 */
public interface ClienteMapper {
    
    public Cliente consultarCliente(@Param("idcli") long id); 
    
    /**
     * Registrar un nuevo item rentado asociado al cliente identificado
     * con 'idc' y relacionado con el item identificado con 'idi'
     * @param id
     * @param idit
     * @param fechainicio
     * @param fechafin 
     */
    public void agregarItemRentadoACliente(@Param("id") long id, 
            @Param("idit") int idit, 
            @Param("fechainicio") Date fechainicio,
            @Param("fechafin") Date fechafin);

    /**
     * Consultar todos los clientes
     * @return 
     */
    public List<Cliente> consultarClientes();
	
	/**
	* Agregar cliente
	*/
	public void agregarCliente(@Param("cli") Cliente cli);
	
	/**
	* Cambia el estado de vetado de un cliente
	*/
	public void setCliente(@Param("idcli") long id, @Param("val") int val);
	
	public ItemRentado consultarItemRentado(@Param("idr") int id);
    
}
