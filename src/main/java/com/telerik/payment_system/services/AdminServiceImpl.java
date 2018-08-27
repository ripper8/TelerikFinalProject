package com.telerik.payment_system.services;

import com.telerik.payment_system.entities.Bill;
import com.telerik.payment_system.entities.Role;
import com.telerik.payment_system.entities.User;
import com.telerik.payment_system.models.viewModels.UserViewModel;
import com.telerik.payment_system.repositories.base.*;
import com.telerik.payment_system.services.base.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final SubscriberRepository subscriberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final BillRepository billRepository;

    private final ServiceRepository serviceRepository;

    private final CurrencyRepository currencyRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository1, RoleRepository roleRepository1, SubscriberRepository subscriberRepository1, BCryptPasswordEncoder bCryptPasswordEncoder1, BillRepository billRepository1, ServiceRepository serviceRepository, CurrencyRepository currencyRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository1;

        this.roleRepository = roleRepository1;
        this.subscriberRepository = subscriberRepository1;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder1;
        this.billRepository = billRepository1;
        this.serviceRepository = serviceRepository;
        this.currencyRepository = currencyRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<>();
        System.out.println(roleRepository.findByAuthority(user.getRoles().get(0).getAuthority()));
        roles.add(roleRepository.findByAuthority(user.getRoles().get(0).getAuthority()));
        user.setRoles(roles);
        this.userRepository.saveAndFlush(user);

    }

    @Override
    public List<UserViewModel> getAllUsers() {
        List<UserViewModel> usersView = new ArrayList<>();
        List<User> users = this.userRepository.findAll();

        return usersView = users.stream()
                .map(x -> modelMapper.map(x, UserViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public void editUser(long id, User feed) {
        User user = this.userRepository.getById(id);
        user.setEmail(feed.getEmail());
        user.setUsername(feed.getUsername());
        user.setEIK(feed.getEIK());

        this.userRepository.saveAndFlush(user);

    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void createPayment(Bill billFeed) {
        String phone = billFeed.getSubscriber().getPhoneNumber();
        billFeed.setSubscriber(subscriberRepository.getByPhoneNumber(phone));
        String serviceName = billFeed.getService().getServiceName();
        billFeed.setService(serviceRepository.getByServiceName(serviceName));
        String currencyName = billFeed.getCurrency().getCurrencyName();
        billFeed.setCurrency(currencyRepository.getByCurrencyName(currencyName));
        this.billRepository.saveAndFlush(billFeed);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = this.userRepository.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username was not found.");
        }

        return user;
    }
}