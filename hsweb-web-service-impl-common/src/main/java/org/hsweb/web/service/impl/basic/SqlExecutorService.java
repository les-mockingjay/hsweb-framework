package org.hsweb.web.service.impl.basic;

import org.hsweb.ezorm.executor.AbstractJdbcSqlExecutor;
import org.hsweb.ezorm.executor.SQL;
import org.hsweb.ezorm.meta.expand.ObjectWrapper;
import org.hsweb.ezorm.meta.expand.SimpleMapWrapper;
import org.hsweb.ezorm.render.support.simple.SimpleSQL;
import org.hsweb.web.core.authorize.ExpressionScopeBean;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service(value = "sqlExecutor")
@Transactional(rollbackFor = Throwable.class)
public class SqlExecutorService extends AbstractJdbcSqlExecutor implements ExpressionScopeBean {

    @Resource
    private DataSource dataSource;

    @Override
    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    @Override
    public void releaseConnection(Connection connection) {
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> list(SQL sql, ObjectWrapper<T> wrapper) throws SQLException {
        return super.list(sql, wrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T single(SQL sql, ObjectWrapper<T> wrapper) throws SQLException {
        return super.single(sql, wrapper);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(SQL sql) throws SQLException {
        List<Map<String, Object>> data = list(sql, new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(SQL sql) throws Exception {
        Map<String, Object> data = single(sql, new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(String sql) throws Exception {
        List<Map<String, Object>> data = list(create(sql), new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> list(String sql, Map<String, Object> param) throws Exception {
        List<Map<String, Object>> data = list(create(sql, param), new SimpleMapWrapper());
        return data;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(String sql) throws Exception {
        Map<String, Object> data = single(create(sql));
        return data;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> single(String sql, Map<String, Object> param) throws Exception {
        Map<String, Object> data = single(create(sql, param));
        return data;
    }

    public SQL create(String sql) {
        return new SimpleSQL(sql);
    }

    public SQL create(String sql, Map<String, Object> param) {
        SimpleSQL sql1 = new SimpleSQL(sql, param);
        return sql1;
    }
}
