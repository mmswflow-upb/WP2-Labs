package com.example.mvcproducts.bootstrap;

import com.example.mvcproducts.domain.Product;
import com.example.mvcproducts.domain.Role;
import com.example.mvcproducts.domain.User;
import com.example.mvcproducts.repositories.ProductRepository;
import com.example.mvcproducts.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  public DataLoader(UserRepository userRepository, ProductRepository productRepository) {
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    PasswordEncoder bcrypt = new BCryptPasswordEncoder();

    // Initialize users
    User user1 = new User("user1", bcrypt.encode("user1"));
    user1.getRoles().add(Role.ROLE_USER);

    User user2 = new User("user2", bcrypt.encode("user2"));
    user2.getRoles().add(Role.ROLE_ADMIN);

    userRepository.save(user1);
    userRepository.save(user2);

    // Initialize products with prices
    productRepository.save(new Product(null, "Laptop", 999.99));
    productRepository.save(new Product(null, "Smartphone", 499.99));
    productRepository.save(new Product(null, "Tablet", 299.99));
  }
}
