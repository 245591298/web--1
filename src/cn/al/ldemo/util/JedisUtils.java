package cn.al.ldemo.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JedisUtils {

    private static JedisPool jedisPool;

    static {
        InputStream ra = null;
        try{
            Properties p = new Properties();
            ra = JedisUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
            p.load(ra);

            JedisPoolConfig jpc = new JedisPoolConfig();
            jpc.setMaxIdle(Integer.parseInt(p.getProperty("maxIdle")));
            jpc.setMaxTotal(Integer.parseInt(p.getProperty("maxTotal")));

            jedisPool = new JedisPool(jpc,p.getProperty("host"),Integer.parseInt(p.getProperty("port")));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ra != null){
                try {
                    ra.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
}
