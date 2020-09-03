package sloth.zhang.test.controller;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.apache.ibatis.annotations.Param;
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

    @GetMapping(value = "/getByName")
    public ApiResponse selectByName(String name){
        if(StringUtils.isEmpty(name)){
            return ApiResponse.error(ResultEnum.PARAM_ERROR);
        }
        ServiceResult serviceResult = userService.selectByName(name);
        if (serviceResult.isSuccess()){
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.error(ResultEnum.USER_NOT_EXIST);
    }

    @PostMapping("/login")
    public ApiResponse Login(@Param("name") String name,@Param("password") String password){

        ServiceResult serviceResult=userService.login(name,password);
        if (serviceResult.isSuccess()){
            return ApiResponse.success(serviceResult.getResult());
        }
        return ApiResponse.error(serviceResult.getMessage());
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

    @PostMapping(value = "/reg")
    public ApiResponse reg(User user){
        if (user==null||StringUtils.isEmpty(user.getName())||StringUtils.isEmpty(user.getPassword())){
            return ApiResponse.error(ResultEnum.USER_NOT_EXIST);
        }
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
