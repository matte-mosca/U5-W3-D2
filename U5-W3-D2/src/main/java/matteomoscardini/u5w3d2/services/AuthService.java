package matteomoscardini.u5w3d2.services;


import matteomoscardini.u5w3d2.entities.Employee;
import matteomoscardini.u5w3d2.exceptions.UnauthorizedException;
import matteomoscardini.u5w3d2.payloads.EmployeeLogin;
import matteomoscardini.u5w3d2.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateEmployeeAndGenerateToken(EmployeeLogin payload) {
        Employee employee = this.employeeService.findByeMail(payload.eMail());
        if (employee.getPassword().equals(payload.password())) {
            return jwtTools.createToken(employee);
        } else {
            throw new UnauthorizedException("Credenziali non validi, Effettua nuovamente il login");
        }


    }
}
