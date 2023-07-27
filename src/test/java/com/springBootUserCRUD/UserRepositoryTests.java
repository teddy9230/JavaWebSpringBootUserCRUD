package com.springBootUserCRUD;

import com.springBootUserCRUD.user.User;
import com.springBootUserCRUD.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("ted@gmail.com");
        user.setPassword("password");
        user.setFirstName("林");
        user.setLastName("于哲");

        User saveUser = repo.save(user);

        Assertions.assertThat(saveUser).isNotNull();

    }

    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> optionalUser = repo.findById(userId);

        User user = optionalUser.get();
        user.setPassword("testpassword");
        repo.save(user);

        User updateUser = repo.findById(userId).get();
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("testpassword");
    }

    @Test
    public void testGet(){
        Integer userId = 12;
        Optional<User> optionalUser = repo.findById(userId);

        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete(){
        Integer userId = 12;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }

}
