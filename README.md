# SpringBoot整合Mybatis

### 1.看下我的目录结构

![image-20200902165459982](C:\Users\m1885\AppData\Roaming\Typora\typora-user-images\image-20200902165459982.png)

### 2.新建数据库

```
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `like` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
```

### 3.配置

yml的相关配置

```
spring:
  datasource:
    name: mytest
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/mytest?useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8

mybatis:
  type-aliases-package: sloth.zhang.test.domain
  mapper-locations: classpath:/mapper/*.xml
```

### 4.新建实体类

**User.java**

```
package sloth.zhang.test.domain;
import lombok.Data;
/**
 * @author Yoga zhang
 * @Type User.java
 * @date 2020/9/2 9:16
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String aihao;
}
```

### 5.Dao层的编写

**UserDao.java**

```
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
```

**UserMapper.xml**

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="sloth.zhang.test.dao.UserDao">

    <!-- 通用查询映射结果 -->
    <resultMap id="User" type="sloth.zhang.test.domain.User">
        <id column="id" property="id" />
        <result column="name" property="name" />
        <result column="password" property="password" />
        <result column="email" property="email" />
        <result column="aihao" property="aihao" />
    </resultMap>

    <select id="selectAll" resultType="sloth.zhang.test.domain.User" >
        select id,name,password,email,aihao from test
    </select>
    
    <select id="selectById" resultType="sloth.zhang.test.domain.User">
        select id,name,password,email,aihao
        from test
        where id=#{id}
    </select>
    
    <insert id="insert">
        insert into test(name,password,email,aihao) values (#{name},#{password},#{email},#{aihao})
    </insert>
    
    <delete id="delete">
        delete from test where id=#{id}
    </delete>
    
    <update id="update">
        update test set name=#{name},password=#{password},email=#{email},aihao=#{aihao}  where id=#{id}
    </update>
</mapper>

```

### 6.Service层的编写

**UserService.java**

```
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
```

**UserServiceImpl.java**

```
package sloth.zhang.test.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sloth.zhang.test.dao.UserDao;
import sloth.zhang.test.domain.User;
import sloth.zhang.test.dto.ServiceResult;
import sloth.zhang.test.service.UserService;
import java.util.Collection;
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
    public ServiceResult selectAll(){
        List<User> users= userDao.selectAll();
        if (!CollectionUtils.isEmpty(users)){
            return ServiceResult.success(users);
        }
        return ServiceResult.error("未查询到数据");
    }
    @Override
    public ServiceResult selectById(Integer id) {
        User user=userDao.selectById(id);
        if (user!=null){
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
        Integer res=userDao.update(user);
        if (res!=0) {
            return ServiceResult.success("修改成功");
        }
        return ServiceResult.error("修改用户失败");
    }
    @Override
    public ServiceResult insert(User user) {
        Integer res=userDao.insert(user);
        if (res!=0) {
            return ServiceResult.success("保存成功");
        }
        return ServiceResult.error("保存用户失败");
    }
}
```

### 7.Controller层的编写

**UserController.java**

```
package sloth.zhang.test.controller;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sloth.zhang.test.domain.User;
import sloth.zhang.test.dto.ApiResponse;
import sloth.zhang.test.dto.ServiceResult;
import sloth.zhang.test.enums.ResultEnum;
import sloth.zhang.test.service.UserService;
/**
 * @author Yoga zhang
 * @Type UserController.java
 * @date 2020/9/2 9:22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    
    @GetMapping(value = "/getById")
    public ApiResponse selectById(Integer id){
        if(StringUtils.isEmpty(id)){
            return ApiResponse.error(ResultEnum.PARAM_ERROR);
        }
        ServiceResult serviceResult = userService.selectById(id);
        if (serviceResult.isSuccess()){
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.error(ResultEnum.USER_NOT_EXIST);
    }

    @GetMapping(value = "/getAll")
    public ApiResponse selectAll(){
        ServiceResult serviceResult=userService.selectAll();
        if (serviceResult.isSuccess()){
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.error(ResultEnum.PARAM_ERROR);
    }

    @PostMapping(value = "/update")
    public ApiResponse update(User user){
        ServiceResult serviceResult=userService.update(user);
        if (serviceResult.isSuccess()){
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.error(serviceResult.getMessage());
    }

    @PostMapping(value = "/insert")
    public ApiResponse insert(User user){
        ServiceResult serviceResult=userService.insert(user);
        if (serviceResult.isSuccess()){
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.error(serviceResult.getMessage());
    }

    @DeleteMapping(value = "/delete")
    public ApiResponse delete(Integer id){
        ServiceResult serviceResult = userService.delete(id);
        if (serviceResult.isSuccess()){
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.error(ResultEnum.USER_NOT_EXIST);
    }
}
```

### 8.枚举类相关代码

**ApiResponse.java**

```
package sloth.zhang.test.dto;
import sloth.zhang.test.enums.ResultEnum;
/**
 * 控制层通用结构
 * @author wangqianlong
 * @create 2019-05-01 19:05
 */
public class ApiResponse<T> {
    private Integer code;
    private String msg;
    private T data;
    public static <T> ApiResponse<T> success(T object) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setData(object);
        apiResponse.setCode(200);
        apiResponse.setMsg("ok");
        return apiResponse;
    }
    public static <T> ApiResponse<T> error(String msg) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(500);
        apiResponse.setMsg(msg);
        return apiResponse;
    }
    public static <T> ApiResponse<T> success(T object, ResultEnum resultEnum) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setData(object);
        apiResponse.setCode(resultEnum.getCode());
        apiResponse.setMsg(resultEnum.getMessage());
        return apiResponse;
    }
    public static <T> ApiResponse<T> error(Integer code, String msg) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(code);
        apiResponse.setMsg(msg);
        return apiResponse;
    }
    public static <T> ApiResponse<T> error(ResultEnum resultEnum) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setCode(resultEnum.getCode());
        apiResponse.setMsg(resultEnum.getMessage());
        return apiResponse;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
```

**ServiceResult.java**

```
package sloth.zhang.test.dto;

import lombok.Data;

/**
 * 服务接口通用结构
 *
 * @author wangqianlong
 * @create 2019-05-01 19:05
 */
@Data
public class ServiceResult<T> {
    private boolean success;
    private String message;
    private T result;
    public ServiceResult(boolean success) {
        this.success = success;
    }
    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public ServiceResult(boolean success, String message, T result) {
        this.success = success;
        this.message = message;
        this.result = result;
    }
    public static <T> ServiceResult<T> success(T result) {
        ServiceResult<T> serviceResult = new ServiceResult<>(true);
        serviceResult.setResult(result);
        return serviceResult;
    }
    public static <T> ServiceResult<T> error(String message) {
        ServiceResult<T> serviceResult = new ServiceResult<>(false);
        serviceResult.setMessage(message);
        return serviceResult;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
}
```

**ResultEnum.java**

```
package sloth.zhang.test.enums;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Yoga zhang
 * @Type ResultEnum.java
 * @date 2020/9/2 12:52
 */
@Getter
public enum ResultEnum {
    SUCCESS(200,"成功"),
    USER_NOT_EXIST(201,"用户不存在"),
    PARAM_ERROR(501,"参数错误"),
    ERROR(500,"错误")
    ;
    private Integer code;
    private String message;
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
```



### 注意事项

- dao-->mapper.xnl-->service中的方法名最好复制，不要重新输入。
- 对于更新，删除，插入方法最好使用整数型来修饰，因为数据库对于这些方法只存在行数的变化。可以通过数据库返回的整数来判断是否成功操作数据库。

