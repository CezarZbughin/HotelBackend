package com.cezar.HotelBackend.service;

import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.repository.EndUserRepository;
import com.cezar.HotelBackend.service.exception.UserNotFoundException;
import com.cezar.HotelBackend.service.exception.UsernameAlreadyExistsException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EndUserService {
    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public Optional<EndUser> getByUsername(String username) {
        return endUserRepository.findByUsername(username);
    }

    public Optional<EndUser> getById(long id) {
        return endUserRepository.findById(id);
    }
    public EndUser createFromRawPassword(String username, String password) throws UsernameAlreadyExistsException {
        EndUser user = new EndUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        try{
            return endUserRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getCause() instanceof ConstraintViolationException) {
                throw new UsernameAlreadyExistsException();
            }
            throw ex;
        }
    }

    public EndUser createFromHashedPassword(String username, String hashedPassword) {
        EndUser user = new EndUser();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        return endUserRepository.save(user);
    }
}
