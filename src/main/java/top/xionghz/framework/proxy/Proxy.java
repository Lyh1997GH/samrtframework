package top.xionghz.framework.proxy;

/**
 * 代理接口
 * @author bj
 * @version 1.0
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain chain) throws Throwable;
}
