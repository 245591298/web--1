package cn.al.ldemo.service;

import cn.al.ldemo.domain.PageBean;
import cn.al.ldemo.domain.Province;
import cn.al.ldemo.domain.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    Boolean findUsernameService(String username);

    void registerUserService(User user);

    String loginService(String username, String password);

    PageBean<User> partPageService(HashMap<String, String[]> condition, String currentPage, String rows);

    void addUserService(User user);

    String provinceService();

    void deleteUserService(String id);

    User updateUserService(String id);

    void updateService(User user, String id);

    void deleteSelectUserService(String[] uids);
}
