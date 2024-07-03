package matteomoscardini.u5w3d2.controllers;


import matteomoscardini.u5w3d2.entities.Device;
import matteomoscardini.u5w3d2.entities.Employee;
import matteomoscardini.u5w3d2.exceptions.BadRequestException;
import matteomoscardini.u5w3d2.payloads.NewEmployeeResponse;
import matteomoscardini.u5w3d2.payloads.PayloadEmployee;
import matteomoscardini.u5w3d2.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    @PreAuthorize(value = "hasAuthority(`ADMIN`)")

        public List<Employee> getAllEmployees(){
        return this.employeeService.getAllEmployees();
}


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private NewEmployeeResponse saveEmployee(@RequestBody @Validated PayloadEmployee body, BindingResult validation){
        if (validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
          return  new NewEmployeeResponse(this.employeeService.saveEmployee(body).getId());
    }

    @GetMapping("/{employeeId}")
    private Employee getSingleEmployee(@PathVariable int employeeId){
        return this.employeeService.findEmployeeById(employeeId);
    }

    @PutMapping("/{employeeId}")
    private Employee findSingleEmployeeAndUpdate(@PathVariable int employeeId, @RequestBody Employee body){
        return this.employeeService.findEmployeeByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteEmployee(@PathVariable int employeeId){
        this.employeeService.findEmployeeByIdAndDelete(employeeId);
    }

    @PatchMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    private void addDevice(@PathVariable int employeeId, @RequestBody Device device){
        this.employeeService.addADevice(employeeId, device);
    }

    @PatchMapping("/{employeeId}/avatar")
    public Employee uploadAvatar(@RequestParam("avatar")MultipartFile file, @PathVariable int employeeId){
        try {
            return employeeService.uploadAvatar(employeeId, file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/me")
    public Employee getProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee){

        return currentAuthenticatedEmployee;
    }

    @PutMapping("/me")
    public Employee updateProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee, @RequestBody Employee updatedEmployee){
        return this.employeeService.findEmployeeByIdAndUpdate(currentAuthenticatedEmployee.getId(), updatedEmployee);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Employee currentAuthenticatedEmployee){
        this.employeeService.findEmployeeById(currentAuthenticatedEmployee.getId());
    }

}
