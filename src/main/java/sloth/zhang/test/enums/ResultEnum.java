package sloth.zhang.test.enums;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yoga zhang
 * @Type ResultEnum.java
 * @date 2020/9/2 12:52
 */

public enum ResultEnum {
    SUCCESS(200,"成功"),
    USER_NOT_EXIST(201,"用户不存在"),
    PASSOWRD_NOT_EXIST(202,"密码不存在"),
    NAME_NOT_EXIST(203,"用户不存在"),
    PARAM_ERROR(501,"参数错误"),
    ERROR(500,"错误"),

    ;

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
