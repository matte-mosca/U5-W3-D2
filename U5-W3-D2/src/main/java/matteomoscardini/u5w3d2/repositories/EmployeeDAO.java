package matteomoscardini.u5w3d2.repositories;


import matteomoscardini.u5w3d2.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByeMail(String eMail);
}
