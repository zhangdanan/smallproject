package sloth.zhang.test.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sloth.zhang.test.TestApplicationTests;
import sloth.zhang.test.domain.User;

import java.util.List;

/**
 * @author Yoga zhang
 * @Type UserDaoTest.java
 * @date 2020/9/2 10:21
 */
class UserDaoTest extends TestApplicationTests {

    @Autowired
    UserDao userDao;

    @Test
    void selectAll() {
        List<User> user=userDao.selectAll();
        System.out.println(user);
    }

    @Test
    void selectById() {
        userDao.selectById(1);
    }

    @Test
    void delete() {
        userDao.delete(2);
    }

    @Test
    void update() {
        User user=userDao.selectById(1);
        user.setId(1);
        user.setEmail("m1885092854@163.com");
        user.setPassword("xxxxx");
        userDao.update(user);
    }

    @Test
    void insert() {
        User user=new User();
        user.setName("zhangdanan");
        user.setPassword("123456789");
        user.setEmail("zheshishaa");
        user.setAihao("喜欢打羽毛球");
        userDao.insert(user);
    }
}