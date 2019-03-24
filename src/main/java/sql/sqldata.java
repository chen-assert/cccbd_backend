package sql;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class sqldata {
    public static String url = "jdbc:mysql://cccbd.top:3306/cccbd?useSSL=false";
    public static String driver = "com.mysql.jdbc.Driver";
    public static String username = "root";
    public static String password = "chen@942202476";
    private static DataSource datasource=null;
    public DataSource getSingletons(){
        if(datasource==null){
            PoolProperties p = new PoolProperties();
            p.setUrl(url);
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername(username);
            p.setPassword(password);

            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

            datasource = new org.apache.tomcat.jdbc.pool.DataSource(p);
            datasource.setPoolProperties(p);
        }
        return datasource;
    }
}
