package cn.al.ldemo.dao.daoimpl;

import cn.al.ldemo.dao.UserDao;
import cn.al.ldemo.domain.Province;
import cn.al.ldemo.domain.User;
import cn.al.ldemo.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    private static JdbcTemplate jt = new JdbcTemplate(JdbcUtils.getDs());
    @Override
    public Boolean findUsernameDao(String username) {
        try {
            String sql = "select *from user where username=?";
            User user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public void registerUserDao(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,?,?)";
        jt.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),
                user.getQq(),user.getEmail(),user.getUsername(),user.getPassword());
    }

    @Override
    public String loginDao(String username, String password) {
        try {
            String sql = "select *from user where username=? and password=?";
            User user = jt.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user.getName();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int totalCountDao(HashMap<String, String[]> condition) {
        String sql = "select count(*) from user where 1=1";

        Set<String> keys = condition.keySet();
        StringBuilder sb = new StringBuilder(sql);
        ArrayList<Object> array = new ArrayList<>();

        for(String key : keys){
            if(key.equals("rows") || key.equals("currentPage")){
                continue;
            }
            String value = condition.get(key)[0];
            if(value != null && !value.equals("")){
                sb.append(" and "+key+" like ?");
                array.add("%"+value+"%");
            }
        }
        sql = sb.toString();

        return jt.queryForObject(sql,Integer.class,array.toArray());
    }

    @Override
    public List<User> listDao(int start, int row, HashMap<String, String[]> condition) {
        String sql = "select * from user where 1=1";

        ArrayList<Object> array = new ArrayList<>();
        StringBuilder sb = new StringBuilder(sql);
        Set<String> keys = condition.keySet();
        for (String key : keys) {
            if(key.equals("rows") || key.equals("currentPage")){
                continue;
            }
            String value = condition.get(key)[0];
            if(value != null && !value.equals("")){
                sb.append(" and "+key+" like ?");
                array.add("%"+value+"%");
            }
        }
        array.add(start);
        array.add(row);
        sb.append(" limit ?,?");
        sql = sb.toString();

        return jt.query(sql,new BeanPropertyRowMapper<User>(User.class),array.toArray());
    }

    @Override
    public void addUserDao(User user) {
        String sql = "insert into user values(null,?,?,?,?,?,?,null,null)";
        jt.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public List<Province> provinceDao() {
        ArrayList<Province> array = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql:///day23", "root", "root");
            String sql = "select *from province";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Province p = new Province();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                array.add(p);
            }
            rs.close();
            ps.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return array;
    }

    @Override
    public void deleteUserDao(int id) {
        String sql = "delete from user where id = ?";
        jt.update(sql,id);
    }

    @Override
    public User updateUserDao(int id) {
        String sql = "select *from user where id = ?";
        return jt.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
    }

    @Override
    public void updateDao(User user, int id) {
        String sql = "update user set name=?,gender=?,age=?,address=?,qq=?,email=? where id=?";
        jt.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),id);
    }
}
