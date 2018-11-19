package top.xionghz.framework.bean;


/**
 * 封装请求信息
 * @author bj
 * @since 1.0.0
 */
public class Request {
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 请求路径
     */
    private String requestpath;

    public Request(String requestMethod, String requestpath) {
        this.requestMethod = requestMethod;
        this.requestpath = requestpath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestpath() {
        return requestpath;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setRequestpath(String requestpath) {
        this.requestpath = requestpath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (requestMethod != null ? !requestMethod.equals(request.requestMethod) : request.requestMethod != null)
            return false;
        return requestpath != null ? requestpath.equals(request.requestpath) : request.requestpath == null;
    }

    @Override
    public int hashCode() {
        int result = requestMethod != null ? requestMethod.hashCode() : 0;
        result = 31 * result + (requestpath != null ? requestpath.hashCode() : 0);
        return result;
    }
}
