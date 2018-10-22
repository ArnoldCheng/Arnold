package cn.zzh.foreground_client.project.entity;

/**
 *
 * @author admin
 * @param <T>
 */
public class Result<T> {
    /**
     * status 状态码
     */
    private boolean status;
    /**
     *  data 泛型类型的data数据
     */
    private T data;
    /**
     *  msg json返回的信息
     */
    private String msg;
    /**
     *  code 错误码
     */
    private int code;
    public Result() {
    }

    public Result(boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    public Result(boolean status){
        this.status =status;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        code = code;
    }

}
