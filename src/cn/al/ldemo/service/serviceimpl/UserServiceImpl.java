package cn.al.ldemo.service.serviceimpl;

import cn.al.ldemo.dao.UserDao;
import cn.al.ldemo.dao.daoimpl.UserDaoImpl;
import cn.al.ldemo.domain.PageBean;
import cn.al.ldemo.domain.Province;
import cn.al.ldemo.domain.User;
import cn.al.ldemo.service.UserService;
import cn.al.ldemo.util.JdbcUtils;
import cn.al.ldemo.util.JedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserDao dao = new UserDaoImpl();
    @Override
    public Boolean findUsernameService(String username) {
        return dao.findUsernameDao(username);
    }

    @Override
    public void registerUserService(User user) {
        dao.registerUserDao(user);
    }

    @Override
    public String loginService(String username, String password) {
        return dao.loginDao(username,password);
    }

    @Override
    public PageBean<User> partPageService(HashMap<String, String[]> condition, String currentPage, String rows) {

        PageBean<User> pb = new PageBean<>();
        int row = Integer.parseInt(rows);
        int currentP = Integer.parseInt(currentPage);

        int totalCount = dao.totalCountDao(condition);
        int totalPage = (totalCount - 1 + row)/row;

        if(currentP > totalPage){
            currentP = totalPage;
        }
        if(currentP < 1){
            currentP = 1;
        }
        int begin = currentP - 2;
        int end = currentP + 2;
        if(end - begin >= totalPage){
            end = totalPage;
            begin = 1;
        }else{
            if(begin < 1){
                begin = 1;
                end = 5;
            }
            if(end > totalPage){
                end = totalPage;
                begin = end - 4;
            }
        }

        int start = (currentP - 1)*row;
        List<User> list = dao.listDao(start,row,condition);

        pb.setBegin(begin);
        pb.setCurrentPage(currentP);
        pb.setEnd(end);
        pb.setRows(row);
        pb.setList(list);
        pb.setTotalCount(totalCount);
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public void addUserService(User user) {
        dao.addUserDao(user);
    }

    @Override
    public String provinceService() {

        Jedis jedis = JedisUtils.getJedis();
        String province = jedis.get("Province");
        if(province == null || province.equals("")){
            System.out.println("缓存中不存在，正在加载数据库。。。");
            List<Province> list =  dao.provinceDao();
            ObjectMapper mapper = new ObjectMapper();
            try {
                province = mapper.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedis.set("Province",province);
        }else{
            System.out.println("缓存中已加载，正在提取。。。");
        }
        return province;
    }

    @Override
    public void deleteUserService(String id) {
        dao.deleteUserDao(Integer.parseInt(id));
    }

    @Override
    public User updateUserService(String id) {
        return dao.updateUserDao(Integer.parseInt(id));
    }

    @Override
    public void updateService(User user, String id) {
        dao.updateDao(user,Integer.parseInt(id));
    }

    @Override
    public void deleteSelectUserService(String[] uids) {
        for (String uid : uids) {
            dao.deleteUserDao(Integer.parseInt(uid));
        }
    }
}
