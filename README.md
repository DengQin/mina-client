# mina-client
mina客户端

使用：
    <bean id="tcpService" class="com.dengqin.mina.service.TcpServiceImpl" destroy-method="destory">
            <constructor-arg value="${server.ip}"/>
            <constructor-arg value="${server.port}"/>
            <constructor-arg value="30000"/>
            <constructor-arg value="UTF-8"/>
    </bean>