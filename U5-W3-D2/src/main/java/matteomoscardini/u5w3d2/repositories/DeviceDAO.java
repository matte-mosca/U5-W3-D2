package matteomoscardini.u5w3d2.repositories;

import matteomoscardini.u5w3d2.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;

@Repository
public interface DeviceDAO extends JpaRepository<Device, Integer> {
}
