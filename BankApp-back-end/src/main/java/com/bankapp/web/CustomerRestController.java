package com.bankapp.web;

import com.bankapp.dtos.CustomerDTO;
import com.bankapp.entities.Customer;
import com.bankapp.exceptions.CustomerNotFoundException;
import com.bankapp.services.BankAccountService;
import com.bankapp.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    @Autowired
    private CustomerService service;
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }
//    @GetMapping("/customers/admin")
//    public List<Customer> findByRole (@RequestParam(name = "role", defaultValue = "") UserRoles role){
//        return customerRepository.findByRole(role);
//    }
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") String customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @GetMapping("/customer/{email}")
    public Customer getCustomerByEmail(@PathVariable(name = "email") String email) throws CustomerNotFoundException {
        return service.fetchUserByEmailId(email);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable String customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setEmailId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable String id){
        bankAccountService.deleteCustomer(id);
    }

    @PostMapping("/register")
    public Customer registerUser(@RequestBody Customer customer) throws Exception {
        String tempEmailId = customer.getEmailId();
        if(tempEmailId!=null && !"".equals(tempEmailId)){
            Customer userObj = service.fetchUserByEmailId(tempEmailId);
            if(userObj != null){
                throw new Exception("user with " + tempEmailId +" is already existed");
            }
        }
        Customer userObj =null;
        userObj = service.saveUser(customer);
        return userObj;
    }

    @PostMapping("/login")
    public Customer loginUser(@RequestBody Customer customer) throws Exception {
        String tempEmail = customer.getEmailId();
        String tempPassword = customer.getPassword();
        String tempRole = customer.getRole();
        Customer userObj = null;
        if(tempEmail != null && tempPassword != null && tempRole!= null){
            userObj = service.fetchUserByEmailAndPasswordAndRole(tempEmail, tempPassword, tempRole);
        }
        if(userObj == null || !tempRole.equals(userObj.getRole())){
            throw new Exception("Bad credentials");
        }
        return userObj;
    }

}
