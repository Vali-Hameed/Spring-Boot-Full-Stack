package com.FullStack;

import com.FullStack.customer.Customer;
import com.FullStack.customer.CustomerRepository;
import com.FullStack.customer.Gender;
import com.FullStack.s3.S3Service;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
// gsgs
    @Bean
    CommandLineRunner runner(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder,
            S3Service s3Service) {
                return args -> {
                    //creatwRandomCustomner(customerRepository, passwordEncoder);
                    s3Service.putObject("fs-spring-customer","foo","Hello World".getBytes());
                    byte[] object = s3Service.getObject("fs-spring-customer","foo");
                    System.out.println("Hooray: "+ new String(object));

                };

    }

    private static void creatwRandomCustomner(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        var faker = new Faker();
        Random random = new Random();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        int age = random.nextInt(16, 99);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com";
        Customer customer = new Customer(
                firstName +  " " + lastName,
                email,
                passwordEncoder.encode("password"),
                age,
                gender);
        customerRepository.save(customer);
        System.out.println(email);
    }

}
