package com.proyect.apidatingappus.service.implementation;

import com.proyect.apidatingappus.controller.dto.auth.ChangePassowrdDto;
import com.proyect.apidatingappus.controller.dto.auth.SignUpDto;
import com.proyect.apidatingappus.controller.mapper.UserMapper;
import com.proyect.apidatingappus.exception.BusinessException;
import com.proyect.apidatingappus.exception.NotFoundException;
import com.proyect.apidatingappus.model.Customer;
import com.proyect.apidatingappus.model.TipoRole;
import com.proyect.apidatingappus.model.User;
import com.proyect.apidatingappus.model.complement.Gender;
import com.proyect.apidatingappus.repository.UserRepository;
import com.proyect.apidatingappus.service.AuthService;
import com.proyect.apidatingappus.service.CustomerService;
import com.proyect.apidatingappus.service.TipoRoleService;
import com.proyect.apidatingappus.util.Constants;
import com.proyect.apidatingappus.util.DateUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    CustomerService customerService;
    TipoRoleService tipoRoleService;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CustomerService customerService, TipoRoleService tipoRoleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.tipoRoleService = tipoRoleService;
    }

    @Override
    @Transactional
    public boolean createUser(SignUpDto signUpDto) {

        if (this.isEmailExist(signUpDto.getEmail())) {
            throw new BusinessException("300", HttpStatus.CONFLICT, "This email already exists.");
        }

        String hashPassword = passwordEncoder.encode(signUpDto.getPassword());
        User user = UserMapper.INSTANCE.toSignUp(signUpDto);
        user.setPassword(hashPassword);

        User entity = userRepository.save(user);
        String[] nombres = signUpDto.getNombres().split(Constants.AMPERSAND);

        Customer customer = getCustomer(signUpDto, nombres);

        customerService.postCustomer(customer, entity.getId());
        return true;
    }

    @Override
    public boolean updatePassword(ChangePassowrdDto changePassowrdDto) {
        User user = userRepository.findOneByEmail(changePassowrdDto.getEmail()).orElseThrow(() -> new NotFoundException("300", "This email already exists.", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(changePassowrdDto.getPasswordOld(), user.getPassword())) {
            throw new BusinessException("300", HttpStatus.CONFLICT, "Password original es incorrecto.");
        }
        user.setPassword(passwordEncoder.encode(changePassowrdDto.getPasswordNew()));

        User entity = userRepository.save(user);
        return userRepository.existsById(entity.getId());
    }

    private Customer getCustomer(SignUpDto signUpDto, String[] nombres) {
        TipoRole tipoRole = tipoRoleService.getTipoRoleByAcronym(signUpDto.getRol());
        Customer customer = new Customer();
        customer.setEmail(signUpDto.getEmail());
        customer.setFirtName(nombres[0]);
        customer.setLastName(nombres[1]);
        customer.setGender(Gender.valueOf(signUpDto.getGenero()));
        customer.setBirthdate(DateUtil.getLocalDateToBirthday(signUpDto.getFechaNacimiento()));
        customer.setPhone(signUpDto.getTelefono());

        customer.setTipoRole(tipoRole);
        return customer;
    }

    private boolean isEmailExist(String email) {
        return userRepository.findOneByEmail(email).isPresent();
    }
}
