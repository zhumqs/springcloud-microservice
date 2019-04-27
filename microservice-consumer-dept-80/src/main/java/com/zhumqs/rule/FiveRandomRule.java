package com.zhumqs.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

public class FiveRandomRule extends AbstractLoadBalancerRule {
    private int total = 0;
    private int currentIndex = 0;

    /**
     *
     * @param loadBalancer 关于所有机器的信息
     * @param key
     * @return 返回应该访问的机器, 机器用server封装起来的
     */
    public Server choose(ILoadBalancer loadBalancer, Object key) {

        if(loadBalancer == null) {
            return null;
        }

        Server server = null;

        while(server == null){

            //当前线程被其他线程终止直接返回null
            if(Thread.interrupted()){
                return null;
            }

            List<Server> upList = loadBalancer.getReachableServers();
            List<Server> allList = loadBalancer.getAllServers();
            
            System.out.println("可用机器： "+upList.size());
            System.out.println("所有机器： "+allList.size());
            
            int serverCount = allList.size();
            if(serverCount == 0) {
                //没有机器返回null
                return null;
            }

            //0~4都是在currentIndex这台机器上
            if(total < 5) {
                server = upList.get(currentIndex);
                total ++ ;
                //当前连续访问超过5次之后换一台并使total=0
            }else {
                total = 0;
                //轮询, 当index超过机器总数从0重新开始
                if(++currentIndex >= upList.size()) {
                    currentIndex = 0;
                }
            }

            /*
             * The only time this should happen is if the server list were
             * somehow trimmed. This is a transient condition. Retry after
             * yielding.
             */
            if(server == null) {
                Thread.yield();
                continue;
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            if(server.isAlive()) {
                return (server);
            }

            server = null;
            Thread.yield();
        }
        return server;
    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }


}
