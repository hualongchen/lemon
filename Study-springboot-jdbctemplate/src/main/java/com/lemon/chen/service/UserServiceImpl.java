package com.lemon.chen.service;

import com.lemon.chen.modul.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chenhualong on 2017/7/10.
 *
 *
 * 注意， 请在各个业务上加载Transactional,不要单独的一个增删改查上面加
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    @Override
    public void create(String name, Integer age) {



        String sql = "insert into mb_user(userid,username,age) VALUES (?,?,?)";


        //第一种塞入方法
        jdbcTemplate.update(sql, new Object[]{1, name, age});

        //第二种塞入方法
        jdbcTemplate.update(sql, 2, name, age);

        //第三种塞入方法

        jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {


                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, "lemon");
                preparedStatement.setInt(3, 44);
            }
        });


    }

    @Transactional
    @Override
    public void deleteByName(String name) {

        jdbcTemplate.update("DELETE from MB_USER WHERE username=?","lemon");

    }

    @Override
    public Integer getAllUsers() {

        final User user = new User();
        String sql = "SELECT userid, username,age from mb_user where userid=?";

        try {
            jdbcTemplate.query(sql, new Object[]{1}, new RowCallbackHandler() {

                @Override
                public void processRow(ResultSet rs) throws SQLException {

                    user.setUserId(rs.getInt(1));
                    user.setUserName(rs.getString(2));
                    user.setAge(rs.getInt(3));
                }
            });
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            //这类异常不用处理， 因为相当于没有这个数据而已
        }

        return 1;
    }

    @Transactional
    @Override
    public void deleteAllUsers() {

        jdbcTemplate.update("DELETE FROM MB_USER");

       // throw new RuntimeException("假如我逻辑错误");
    }

    @Transactional
    @Override
    public void deleteAllUsersFroTransaction() {

        jdbcTemplate.update("DELETE FROM MB_USER");

        throw new RuntimeException("假如我逻辑错误");
    }
}
