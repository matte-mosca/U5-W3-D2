package matteomoscardini.u5w3d2.controllers;


import matteomoscardini.u5w3d2.exceptions.BadRequestException;
import matteomoscardini.u5w3d2.payloads.EmployeeLogin;
import matteomoscardini.u5w3d2.payloads.EmployeeLoginResponse;
import matteomoscardini.u5w3d2.payloads.NewEmployeeResponse;
import matteomoscardini.u5w3d2.payloads.PayloadEmployee;
import matteomoscardini.u5w3d2.services.AuthService;
import matteomoscardini.u5w3d2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public EmployeeLoginResponse login(@RequestBody EmployeeLogin loginPayload){
    return new EmployeeLoginResponse(this.authService.authenticateEmployeeAndGenerateToken(loginPayload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeeResponse saveEmployee(@RequestBody @Validated PayloadEmployee loginPayload, BindingResult validation) {

        if(validation.hasErrors()) {  throw new BadRequestException(validation.getAllErrors());
        }

        return new NewEmployeeResponse(this.employeeService.saveEmployee(loginPayload).getId());
    }

}
