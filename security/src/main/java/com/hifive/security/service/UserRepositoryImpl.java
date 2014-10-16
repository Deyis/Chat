package com.hifive.security.service;

import com.hifive.common.repository.AbstractRepository;
import com.hifive.security.model.Authority;
import com.hifive.security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("userAuthService")
@Transactional
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getSingleByNamedQuery("User.findByNameWithAuth", "userName", username);
    }

    @Override
    public User registerUser(String userName, String password) {
        User newUser = new User();
        newUser.setUsername(userName);
        newUser.setPassword(password);
        newUser.setEnabled(true);
        return merge(newUser);
    }

    @Override
    public User registerUser(String userName, String password, String authority) {
        return grantRole(registerUser(userName, password), authority);
    }

    @Override
    public User grantRole(Long userId, String authority) {
        return grantRole(findById(userId), authority);
    }

    @Override
    public User grantRole(User user, String authority) {
        Authority auth = (Authority) createAndFillQuery("Authority.getByName", "authority", authority).getSingleResult();
        user.getAuthorities().add(auth);
        return merge(user);
    }

    @Override
    public List<User> getUsersByIds(List<Long> ids) {
        return getListByNamedQuery("Users.findByIds", "ids", ids);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
