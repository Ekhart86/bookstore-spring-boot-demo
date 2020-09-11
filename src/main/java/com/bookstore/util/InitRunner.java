package com.bookstore.util;

import com.bookstore.entity.Users;
import com.bookstore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(InitRunner.class);
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public InitRunner(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!repository.existsByUsername("admin")) {
            Users admin = new Users();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            Users newAdmin = repository.save(admin);
            logger.info("Create admin - {}", newAdmin.getUsername());
        } else {
            logger.info("Admin already exist.");
        }
    }
}
