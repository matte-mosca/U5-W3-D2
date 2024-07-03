package matteomoscardini.u5w3d2.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import matteomoscardini.u5w3d2.entities.Device;
import matteomoscardini.u5w3d2.entities.Employee;
import matteomoscardini.u5w3d2.exceptions.NotFoundException;
import matteomoscardini.u5w3d2.payloads.PayloadEmployee;
import matteomoscardini.u5w3d2.repositories.DeviceDAO;
import matteomoscardini.u5w3d2.repositories.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeDAO employeeDAO;

    @Autowired
    DeviceDAO deviceDAO;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private PasswordEncoder bcrypt;

    public List<Employee> getAllEmployees(){
        return this.employeeDAO.findAll();
    }

    public Employee saveEmployee(PayloadEmployee body){
        Employee newEmployee = new Employee(
                body.username(),
                body.name(),
                body.surname(),
                body.eMail(),
                "https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname(),
                new ArrayList<>(),
                bcrypt.encode(body.password())
        );

        return this.employeeDAO.save(newEmployee);
    }

    public Employee findEmployeeById(int id){
        return employeeDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Employee findEmployeeByIdAndUpdate(int id, Employee updatedBody){
        Optional<Employee> optionalEmployee = employeeDAO.findById(id);

        if (optionalEmployee.isPresent()){
            Employee found = optionalEmployee.get();
            found.setUsername(updatedBody.getUsername());
            found.setName(updatedBody.getName());
            found.setSurname(updatedBody.getSurname());
            found.setEMail(updatedBody.getEMail());

            return this.employeeDAO.save(found);
        }else {
            throw new NotFoundException(id);
        }
    }

    public void findEmployeeByIdAndDelete(int id){
        Optional<Employee> optionalEmployee = employeeDAO.findById(id);

        if (optionalEmployee.isPresent()){
            Employee found = optionalEmployee.get();
            this.employeeDAO.delete(found);
        }else{
            throw new NotFoundException(id);
        }
    }

    public void addADevice(int employeeId, Device device){
        Employee employee = employeeDAO.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));


        device.setEmployee(employee);
        device.setStatus("not available");

        List<Device> devicesList = employee.getDevicesList();
        devicesList.add(device);
        employee.setDevicesList(devicesList);

        employeeDAO.save(employee);
        }



        public Employee uploadAvatar(int id, MultipartFile file)throws IOException {
        Employee found = this.findEmployeeById(id);
        String avatarUrl = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatarUrl(avatarUrl);
        return this.employeeDAO.save(found);
        }

        public Employee findByeMail(String email){
        return employeeDAO.findByeMail(email).orElseThrow(() -> new NotFoundException("No Employee with the searched eMail " + email + " found"));
        }
    }

