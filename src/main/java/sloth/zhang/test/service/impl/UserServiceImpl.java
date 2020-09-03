package sloth.zhang.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sloth.zhang.test.dao.UserDao;
import sloth.zhang.test.domain.User;
import sloth.zhang.test.dto.ServiceResult;
import sloth.zhang.test.service.UserService;

import java.util.List;

/**
 * @author Yoga zhang
 * @Type UserServiceImpl.java
 * @date 2020/9/2 9:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ServiceResult selectAll() {
        List<User> users = userDao.selectAll();
        if (!CollectionUtils.isEmpty(users)) {
            return ServiceResult.success(users);
        }
        return ServiceResult.error("未查询到数据");
    }

    @Override
    public ServiceResult selectById(Integer id) {
        User user = userDao.selectById(id);
        if (user != null) {
            return ServiceResult.success(user);
        }
        return ServiceResult.error("未查询到数据");
    }

    @Override
    public ServiceResult selectByName(String name) {
        User user = userDao.selectByName(name);
        if (user != null) {
            return ServiceResult.success(user);
        }
        return ServiceResult.error("未查询到数据");
    }

    @Override
    public ServiceResult delete(Integer id) {
        return ServiceResult.success(userDao.delete(id));

    }

    @Override
    public ServiceResult update(User user) {
        Integer res = userDao.update(user);
        if (res != 0) {
            return ServiceResult.success("修改成功");
        }
        return ServiceResult.error("修改用户失败");
    }

    @Override
    public ServiceResult insert(User user) {
        Integer res = userDao.insert(user);
        if (res != 0) {
            return ServiceResult.success("保存成功");
        }
        return ServiceResult.error("保存用户失败");
    }

    @Override
    public ServiceResult<Object> login(String name, String password) {
        User user = userDao.login(name,password);
        if (user == null || StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
            return ServiceResult.error("用户不存在啊");
        } else if (user.getName().equals(name) && !user.getPassword().equals(password)) {
            return ServiceResult.error("密码错误，请重新登陆");
        } else if (!user.getName().equals(name) && user.getPassword().equals(password)) {
            return ServiceResult.error("用户名错误，重新登陆");
        } else if (user.getName().equals(name) && user.getPassword().equals(password)) {
            return ServiceResult.success("登陆成功");
        }
        return ServiceResult.error("无心插柳柳成阴");
    }
}
