package com.caetp.digiex.service;

import com.alibaba.fastjson.JSON;
import com.caetp.digiex.config.RabbitMqConfig;
import com.caetp.digiex.utli.common.GetMemberId;
import com.caetp.digiex.utli.mq.Send2Mq;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**Created by gaoyx on 2019/4/26.
 * WebSocket是类似客户端服务端的形式(采用ws协议)，
 * 那么这里的WebSocketServer其实就相当于一个ws协议的Controller
 * websocket建立一次连接就会new一个WebSocketToApp2对象,所以这里是多例的
 */
@Data
@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketToAppService {
    static Logger log = LoggerFactory.getLogger(WebSocketToAppService.class);
     /*静态变量，用来记录当前在线连接数.应该把它设计成线程安全的.*/
    private static int onlineCount = 0;
    /* concurrent包的线程安全Set,用来存放每个客户端对应的WebSocketToApp2*/
    public static ConcurrentHashMap<String, WebSocketToAppService> webSocketToApp2S = new ConcurrentHashMap<>();
    // 存放请求参数
    public  static ConcurrentHashMap<String,String> reqParamters = new ConcurrentHashMap<>();
    // 存放请求类型
    public  static ConcurrentHashMap<String,String> reqType = new ConcurrentHashMap<>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    // memberid
    private  String memberid;
    private static RabbitTemplate rabbitTemplate;

    @Autowired
    Mt5HandleService mt5HandleService;
    @Autowired
    public WebSocketToAppService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public WebSocketToAppService() { }

    /**连接建立成功调用的方法
     * @param session hhh
     *
     */
    @OnOpen
    public void onOpent(Session session){
        this.session = session;
        //连接数量+1
        addOnlineCount();
        log.info("前端连接后台WebSocket服务成功");
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * 连接关闭调用的方法*/
    @OnClose
    public void onClose(){
        //将当前连接的websocket对象从集合中删除，让连接数正确
        if (memberid != null) {
            webSocketToApp2S.remove(memberid);
        }
        subOnlineCount();
        log.info("有一连接关闭！当前连接数为" + getOnlineCount());
    }

    /**收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 与客户端的会话
     */
    @OnMessage
    public void onMessage(String message, Session session){
        Map map = (Map) JSON.parseObject(message);
        // 拿到reqid ,从中取出memberid
        String reqid = (String) map.get("reqid");
        log.info("收到来着用户:" + reqid + "的请求参数:" + message);
        // 将请求参数存放起来
        reqParamters.put(reqid, message);
        // 将请求类型存起来
        String reqTypeStr = (String)map.get("reqtype");
        if (!StringUtils.isEmpty(reqTypeStr)) {
            reqType.put(reqid, reqTypeStr);
        }
        memberid = GetMemberId.getMemberId(message);
        if (!StringUtils.isEmpty(memberid)) {
            webSocketToApp2S.put(memberid, this);
            Send2Mq.sendObject2Mq(message, RabbitMqConfig.EXCHANGE_QUEUE_PARAMETER,
                    RabbitMqConfig.ROUTINGKEY_QUEUE_PARAMETER, rabbitTemplate);
           log.info("请求参数发送到mq完成");
        }
    }

    @OnError
    public void onError(Session session, Throwable e){
        Exception e2 = (Exception) e;
        e2.printStackTrace();
        log.error("发生错误" + e2.toString());
        try {
            sendMessage(e2.toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     *  从mq获取到的消息
     * @param msg :解码后的消息
     */
    @RabbitListener(queues = RabbitMqConfig.QUEUE_MESSAGE)
    public void getMessageFromMqToApp(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag){
        try {
            // 从mq中取出消息,返回给前端
            Map mt5ResultMap = (Map) JSON.parseObject(msg);
            log.info("从mq中接受到返回给app的数据:" + msg);
            String reqid = (String) mt5ResultMap.get("reqid");
            String[] splits = reqid.split("\\.");
            reqid = splits[0] + "." + splits[1];
            // 取出请求参数以后，将集合中的请求参数删除
            String reqParamer = (String) reqParamters.get(reqid);
            reqParamters.remove(reqid);
            log.info("本次请求为:"+ reqid +"参数为:" + reqParamer );
            log.info("返回参数：" + msg );
            // 将请求结果返回给前端
            String[] reqids = reqid.split("\\.");
            String memberid = splits[0];
            if (!StringUtils.isEmpty(memberid) && webSocketToApp2S.size() != 0) {
                WebSocketToAppService webSocketToAppService = webSocketToApp2S.get(memberid);// splits[0] --> memberid}
                if (webSocketToAppService != null) {
                    webSocketToAppService.sendMessage(msg);
                }
            }
                channel.basicAck(tag, false);
            } catch (Exception e) {
            e.printStackTrace();
            try {
                // 出现异常，拒绝确认   第一个参数，这个消息的标签，第二个参数:true则后面的所有消息都被拒绝接受了，
                // false表示只拒绝当前这个消息.  第三个参数:ture表示消息重新去排队等待消息，false表示丢弃或者放入死信队列
                channel.basicNack(tag,false,false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    /**服务器向客户端主动推送消息
     * @param message 消息
     */
    public void sendMessage(String message) throws IOException {
        if (this.session != null){
            this.session.getBasicRemote().sendText(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketToAppService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketToAppService.onlineCount--;
    }

}

