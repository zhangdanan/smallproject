package sloth.zhang.test.service;

import sloth.zhang.test.domain.User;
import sloth.zhang.test.dto.ServiceResult;

import java.util.List;

/**
 * @author Yoga zhang
 * @Type UserService.java
 * @date 2020/9/2 9:23
 */

public interface UserService {

    ServiceResult selectAll();

    ServiceResult selectById(Integer id);

    ServiceResult delete(Integer id);

    ServiceResult update(User user);

    ServiceResult insert(User user);



}
