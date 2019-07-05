package com.schx.docadmin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**   
 * Redis操作接口
 *
 */
@Service
public class RedisClient implements Serializable {
    private  JedisPool pool = null;

	@Value("${redis.host}")
    private String host;

	@Value("${redis.port}")
    private int port;

	@Value("${redis.timeout}")
    private int timeout;

	@Value("${redis.password}")
    private String password;
    /**
     * 构建redis连接池
     *
     * @return JedisPool
     */
    public  JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(-1);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 1000);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config,host ,port,timeout,password);
        }
        return pool;
    }
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
    public  String get(String key){
        String value = null;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
        	if(jedis!=null) jedis.close();
        }
        
        return value;
    }
	/**
	 * 获取数据
	 *
	 * @param key
	 * @return
	 */
	public  byte[] get(byte[] key){
		byte[] value = null;
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}

		return value;
	}
    /**
     * 获取数据<br>
     * 性能问题 禁用，先存有序集合set
     * @return
     */
    @Deprecated
    public  Set<String>  findKeys(String pattern){
    	Set<String> value = null;
    	JedisPool pool = null;
    	Jedis jedis = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		value = jedis.keys(pattern);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
    		if(jedis!=null) jedis.close();
    	}
    	return value;
    }
    
    /**写入有序集合（value不可重复）
     * @param key 
     * @param score 分数 （可重复）
     * @param member 值
     */
    public void zadd(String key,double score,String member){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		jedis.zadd(key, score, member);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
    		if(jedis!=null) jedis.close();
    	}
    }
    /**查找有序集合（value不可重复）
     * @param key 
     * @param start 0开始
     * @param end -1全部
     */
    public Set<String> zrange (String key,long start,long end){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	Set<String> value = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		value= jedis.zrange(key, start, end);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
    		if(jedis!=null) jedis.close();
    	}
    	return value;
    }
    /**查找有序集合,从低到高查找
     * @param key 
     * @param min 最小值（min表示不包括min值
     * @param max +inf最大
     */
    public Set<String> zrangeByScore (String key, String min, String max, int offset, int count){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	Set<String> value = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		value= jedis.zrangeByScore(key, min, max, offset, count);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(jedis!=null) jedis.close();
    	}
    	return value;
    }
    /**查找有序集合,从高到底
     * @param key 
     * @param min 最小值（min表示不包括min值
     * @param max +inf最大
     */
    public Set<String> zrevrangeByScore (String key, String max, String min, int offset, int count){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	Set<String> value = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		value= jedis.zrevrangeByScore(key, max, min, offset, count);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(jedis!=null) jedis.close();
    	}
    	return value;
    }
    /**删除有序集合
     * @param key 
     * @param start 最小值（min表示不包括min值
     * @param end +inf最大
     */
    public Long zremrangeByScore (String key, String start, String end){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	Long value = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		value=jedis.zremrangeByScore(key, start, end);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(jedis!=null) jedis.close();
    	}
    	return value;
    }
    /**
     * 设置数据
     * 
     * @param key
     * @return
     */
    public  void set(String key,String value){
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
        	if(jedis!=null) jedis.close();
        }
    }
	/**
	 * 设置数据
	 *
	 * @param key
	 * @return
	 */
	public  void set(byte[] key,byte[] value){
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.set(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
	}
    /**
     * 清除数据
     * @return
     */
    public  void flushall(){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		jedis.flushAll();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
    		if(jedis!=null) jedis.close();
    	}
    }
    /**
     * 过期
     * 
     * @param key
     * @return
     */
    public  void expire(String key,int seconds){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		jedis.expire(key, seconds);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
    		if(jedis!=null) jedis.close();
    	}
    }
    /**
     * 过期
     *
     * @param key
     * @return
     */
    public  void expire(byte[] key,int seconds){
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
            if(jedis!=null) jedis.close();
        }
    }
    /**
     * 删除数据
     * 
     * @param key
     * @return
     */
    public  long del(String... key ){
        long value=0 ;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
        	if(jedis!=null) jedis.close();
        }
        return value;
    }
    /**
     * 删除数据列表
     * 
     * @param keys
     * @return
     */
    public  long delListkey(Collection<String> keys ){
        long value=0 ;
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            for(String key:keys){
            	value = jedis.del(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
        	if(jedis!=null) jedis.close();
        }
        return value;
    }
    /**return the number of elements inside the list after the push operation.
     * @param key
     * @param value
     */
    public  long lpush(String key,String value){
    	   JedisPool pool = null;
           Jedis jedis = null;
           long ret=-1;
           try {
               pool = getPool();
               jedis = pool.getResource();
               ret = jedis.lpush(key, value);
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
        	   if(jedis!=null) jedis.close();
           }
           return ret;
    }
    /**
     * @param key
     * @param start
     * @param end
     */
    public  List<String> lrange (String key,long start, long end){
    	   JedisPool pool = null;
           Jedis jedis = null;
           List<String> ret=null;
           try {
               pool = getPool();
               jedis = pool.getResource();
               ret= jedis.lrange(key, start, end);
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
        	   if(jedis!=null) jedis.close();
           }
           return ret;
    }
    /** 
     * @param key
     */
    public  long llen (String key){
    	JedisPool pool = null;
    	Jedis jedis = null;
    	long ret=0;
    	try {
    		pool = getPool();
    		jedis = pool.getResource();
    		ret= jedis.llen(key);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if(jedis!=null) jedis.close();
    	}
    	return ret;
    }
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public Long zrank(String key, String member) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long ret=null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ret= jedis.zrank(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
		return ret;
    }

	public Double zscore(String key, String member) {
		JedisPool pool = null;
		Jedis jedis = null;
		Double ret=null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ret= jedis.zscore(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
		return ret;
	}

	public Set<String> zrevrange(String key, int start, int end) {
		JedisPool pool = null;
		Jedis jedis = null;
		Set<String> ret= Collections.EMPTY_SET;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ret= jedis.zrevrange(key,start,end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
		return  ret;
	}
	/**有序集合成员数*/
	public Long zcard(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long ret=0l;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ret= jedis.zcard(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
		return  ret;
	}
	/**移出有序集合指定区间成员*/
	public Long zremrangeByRank(String key,int start,int stop) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long ret=0l;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ret= jedis.zremrangeByRank(key,start,stop);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
		return  ret;
	}

    public Long incr(String key) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long ret=0l;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ret= jedis.incr(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
		return  ret;
    }
	public Long incrBy(String key,long value) {
		JedisPool pool = null;
		Jedis jedis = null;
		Long ret=0l;
		try {
			pool = getPool();
			jedis = pool.getResource();
			ret= jedis.incrBy(key,value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//返还到连接池Resource cleanup should be done using @see {@link redis.clients.jedis.Jedis#close()}
			if(jedis!=null) jedis.close();
		}
		return  ret;
	}
}