package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.Item;

/**
 *
 * @author 2106913
 */
public interface ItemMapper {
    
    
    public List<Item> consultarItems();        
    
    public Item consultarItem(@Param("id_it") int id);
    
    public void insertarItem(@Param("item") Item it);
	
	public void setItem(@Param("id_it") int id, @Param("tar") long tarifa);
	
	public List<Item> consultarItemsLibres();

}
