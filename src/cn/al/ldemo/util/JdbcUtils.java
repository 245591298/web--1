package cn.al.ldemo.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DataSource ds;

    static {
        InputStream is = null;
        try{
            Properties p = new Properties();
            is = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            p.load(is);
            ds = DruidDataSourceFactory.createDataSource(p);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null) {
            try {
                    is.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static DataSource getDs(){
        return ds;
    }

    public static Connection getCon() throws SQLException {
        return ds.getConnection();
    }
}
