package sloth.zhang.test.dao;

import org.apache.ibatis.annotations.Mapper;
import sloth.zhang.test.domain.User;

import java.util.List;

/**
 * @author Yoga zhang
 * @Type UserDao.java
 * @date 2020/9/2 9:24
 */

@Mapper
public interface UserDao {
    List<User>  selectAll();

    User selectById(Integer id);

    Integer delete(Integer id);

    Integer update(User user);

    Integer insert(User user);

}
