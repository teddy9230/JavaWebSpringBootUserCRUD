package com.springBootUserCRUD.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repo;

    public List<User> listAll(){
        return (List<User>) repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(Integer id) throws UserNotFoundException{
        Optional<User> result = repo.findById(id);

        if (result.isPresent()){
            return result.get();
        }

        throw new UserNotFoundException("找不到使用者!!!");
    }

    public void delete(Integer id) throws UserNotFoundException{
        Long count = repo.countById(id);

        if(count == null || count == 0){
            throw new UserNotFoundException("找不到使用者 id = " + id + " 的使用者");
        }

        repo.deleteById(id);

    }

}
