package cn.al.ldemo.dao;

import cn.al.ldemo.domain.Province;
import cn.al.ldemo.domain.User;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    Boolean findUsernameDao(String username);

    void registerUserDao(User user);

    String loginDao(String username, String password);

    int totalCountDao(HashMap<String, String[]> condition);

    List<User> listDao(int start, int row, HashMap<String, String[]> condition);

    void addUserDao(User user);

    List<Province> provinceDao();

    void deleteUserDao(int id);

    User updateUserDao(int id);

    void updateDao(User user, int id);
}
