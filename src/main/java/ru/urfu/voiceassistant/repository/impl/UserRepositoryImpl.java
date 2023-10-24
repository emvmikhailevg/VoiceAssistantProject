package ru.urfu.voiceassistant.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Component;
import ru.urfu.voiceassistant.dao.UserDAO;
import ru.urfu.voiceassistant.model.UserModel;
import ru.urfu.voiceassistant.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * UserRepositoryImpl class implementing the UserRepository
 * interface is needed to search for specific users in the database
 * @author Емельянов Микхаил <emelianov.mikhail@urfu.me>
 * @see UserDAO
 * @see UserModel
 */
@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserDAO userDAO;

    @Autowired
    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserModel findUserByLoginAndPassword(String login, String password) {
        for (UserModel user : userDAO.getUsersList()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public Boolean hasDuplicates(String login) {
        Set<String> uniqueLogins = new HashSet<>();

        for (UserModel user : userDAO.getUsersList()) {
            String userLogin = user.getLogin();
            uniqueLogins.add(userLogin);
        }

        return uniqueLogins.size() == 0;
    }
    @Override
    public void flush() {}

    @Override
    public <S extends UserModel> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UserModel> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<UserModel> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserModel getOne(Integer integer) {
        return null;
    }

    @Override
    public UserModel getById(Integer integer) {
        return null;
    }

    @Override
    public UserModel getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends UserModel> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserModel> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends UserModel> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends UserModel> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserModel> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserModel> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UserModel, R> R findBy
            (Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends UserModel> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserModel> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<UserModel> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    public List<UserModel> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(UserModel entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserModel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<UserModel> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return null;
    }
}
