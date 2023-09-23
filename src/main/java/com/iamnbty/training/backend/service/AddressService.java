package com.iamnbty.training.backend.service;

import com.iamnbty.training.backend.entity.Address;
import com.iamnbty.training.backend.entity.Social;
import com.iamnbty.training.backend.entity.User;
import com.iamnbty.training.backend.exception.BaseException;
import com.iamnbty.training.backend.repository.AddressRepository;
import com.iamnbty.training.backend.repository.SocialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    public final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public List<Address> findByUser(User user) throws BaseException {
        return repository.findByUser(user);
    }

    public Address create(User user,String line1, String line2, String zipcode) {

        // Create
        Address  entity = new Address();

        entity.setUser(user);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setZipcode(zipcode);

        return repository.save(entity);
    }

}
