package net.bounceme.chronos.clientesapi.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.clientesapi.aspects.BeforeSave;
import net.bounceme.chronos.clientesapi.models.entity.Cliente;
import net.bounceme.chronos.clientesapi.models.entity.Region;


/**
 * @author federico
 *
 */
@Repository
public interface RegionDao extends JpaRepository<Region, Long> {
	
	@BeforeSave
	<S extends Cliente> S save(S entity);
	
	@BeforeSave
	<S extends Cliente> S saveAndFlush(S entity);
	
	@Query("from Region")
	List<Region> findAllRegiones();
}
