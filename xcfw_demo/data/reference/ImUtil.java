package com.xiaocoder.mqtt;//package com.qlk.ymz.util;
//
//import android.os.Handler;
//import android.util.Log;
//import com.zhuyiqi.BaseApplication;
//import com.zhuyiqi.api.IApiCallBack;
//import com.zhuyiqi.config.GlobalConfig;
//import org.eclipse.paho.client.mqttv3.*;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * @description 即时通讯工具类
// * <p/>
// * Created by xiangfei on 3/25/14.
// */
//public class ImUtil {
//
//    private static MqttAsyncClient client;
//    private static MqttMessage message = new MqttMessage();
//    private static MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
//    private static Handler handler; // zaixian
//    private static Handler handler2; //zaixianlixian
//    private static String readOrNotId;
//    private static boolean connected = false;
//
//    /**
//     *
//     * 初始化mqtt客户端
//     */
//    static {
//        try {
//            String str = "" + Math.random();
//
//            // host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
//            client = new MqttAsyncClient(GlobalConfig.HOST, str, new MemoryPersistence());
//            //设置回调
//            client.setCallback(new ImClientMessageListener());
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 连接服务器
//     *
//     * @param userId
//     */
//    public static void connect(String userId) {
//        try {
//            JSONObject json = new JSONObject();
//            json.put("userid", userId);
//            //设置最终端口的通知消息
//            mqttConnectOptions.setWill("/mqttdown", json.toString().getBytes(), 1, false);
//            mqttConnectOptions.setKeepAliveInterval(5);
//            mqttConnectOptions.setConnectionTimeout(5);
//            client.connect(mqttConnectOptions, null, new InitializerListener());
//            LogUtil.d("test", "一直在链接");
//            while (!connected) {
//                try {
//                    Thread.sleep(200);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            LogUtil.d("test", "没停止拦截链接");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * 关掉链接
//     */
//    public static void disconnect() {
//        try {
//            client.disconnect();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 设置在线状态
//     *
//     * @param userId    登录用户
//     * @param gardenIds 用户关联小区编号，多个用逗号分开 如121212，122112，1212
//     */
//    public static void setOnline(String userId, String gardenIds) {
//        LogUtil.d("test", client.isConnected() + "==");
//
//        try {
//            String channels = "";
//            String[] gardens = gardenIds.split(",");
//            for (int i = 0; i < gardens.length; i++) {
//                if (i != gardens.length - 1) {
//                    channels += "/gdn/" + gardens[i] + ",";
//                } else {
//                    channels += "/gdn/" + gardens[i];
//                }
//            }
//            JSONObject json = new JSONObject();
//            json.put("status", 1);
//            json.put("userid", userId);
//            json.put("ext_channels", channels);
//            message = new MqttMessage();
//            message.setQos(1);
//            message.setPayload(json.toString().getBytes());
//            client.publish("/im/status", message);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 设置离线状态
//     *
//     * @param userId    登录用户
//     * @param gardenIds 用户关联小区编号，多个用逗号分开 如121212，122112，1212
//     */
//    public static void setOffline(String userId, String gardenIds) {
//        try {
//            String channels = "";
//            String[] gardens = gardenIds.split(",");
//            for (int i = 0; i < gardens.length; i++) {
//                if (i != gardens.length - 1) {
//                    channels += "/gdn/" + gardens[i] + ",";
//                } else {
//                    channels += "/gdn/" + gardens[i];
//                }
//            }
//            Log.i("test", channels + "sddssd" + userId);
//            JSONObject json = new JSONObject();
//            json.put("status", 0);
//            json.put("userid", userId);
//            json.put("ext_channels", channels);
//            message = new MqttMessage();
//            message.setQos(1);
//            message.setPayload(json.toString().getBytes());
//            client.publish("/im/status", message);
//            disconnect();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 订阅消息
//     *
//     * @param userId  登录用户家家号
//     * @param handler 处理
//     */
//    public static void subscribe(String userId, Handler handler, Handler handler2) {
//        try {
//            if (handler != null) {
//                ImUtil.handler = handler;
//            }
//            if (handler2 != null) {
//                ImUtil.handler2 = handler2;
//            }
//
//            client.subscribe("/usr/" + userId, 1);
//
////            }
//
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 发布消息
//     *
//     * @param msg        内容
//     * @param fromUserId 发送用户家家号
//     * @param toUserId   接受用户家家号
//     */
//    public static void publish(String msg, String fromUserId, String toUserId) {
//        try {
//            JSONObject json = new JSONObject();
//            json.put("from", fromUserId);
//            json.put("to", toUserId);
//            json.put("payload", msg);
//            json.put("createdate", System.currentTimeMillis());
//            json.put("client", 1);
//            message.setPayload(json.toString().getBytes());
//            LogUtil.d("test", "发送:" + json.toString());
//
//            client.publish("/usr/" + toUserId, message);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 订阅离线消息
//     *
//     * @param userId
//     * @param handler [{"count":"6","from":"16000001"},{"count":"8","from":"16888888"}]
//     */
//    public static void subscribeOfflineMessage(String userId, Handler handler) {
//        try {
//            ImUtil.handler = handler;
//            client.subscribe("/im/loginfeed/" + userId, 1);
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取离线数据
//     *
//     * @param userId      登录用户编号
//     * @param fromUserId  留言用户编号
//     * @param apiCallBack <pre>
//     *                                         [{"date":1396540800000,"from":"36991503962092","payload":"佛祖宗","to":"30734274351076"}]
//     *                                                                             </pre>
//     */
//    public static void getOfflineMessage(String userId, String fromUserId, IApiCallBack apiCallBack) {
//        AsyncHttpUtil.getData(GlobalConfig.IM_URL+"/api_offlineMessageQuery?userid=" + userId + "&from=" + fromUserId, apiCallBack, false);
//    }
//
//    public static void getMessageRecord(String userId, String fromUserId, IApiCallBack apiCallBack) {
//
//    }
//
//
//    private static class InitializerListener implements IMqttActionListener {
//
//        @Override
//        public void onSuccess(IMqttToken asyncActionToken) {
//            connected = true;
//
//        }
//
//        @Override
//        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//            connected = false;
//
//        }
//
//    }
//
//    private static class ImClientMessageListener implements MqttCallback {
//
//        @Override
//        public void connectionLost(Throwable cause) {
//            // 连接丢失后，一般在这里面进行重连
//            LogUtil.i("test", "connectionLost----------");
//        }
//
//        @Override
//        public void deliveryComplete(IMqttDeliveryToken token) {
//            // publish后会执行到这里
//            LogUtil.i("test", "deliveryComplete---------"
//                    + token.isComplete());
//        }
//
//        @Override
//        public void messageArrived(String topicName, MqttMessage message)
//                throws Exception {
//            // 订阅后会执行
//            LogUtil.i("test", "----------" + message.toString() + ":" + topicName);
//            try {
//                if (topicName.startsWith("/im")) {
//                    //{"dummy":true,"user_unread":[{"count":"3","from":"30734274351076"}],"userid":"36991503979555"}
//                    LogUtil.d("test>>>>>>>>>>", "离线" + message);
//                    android.os.Message mess = new android.os.Message();
//                    JSONObject json = new JSONObject(message.toString());
//                    mess.what = 66;
//                    mess.obj = json/*.getString("payload")*/;
//                    handler.sendMessage(mess);
//                } else {
//                    LogUtil.d("test<<<<<<<<<<<<<<", "在线" + message);
//                    //获取发送信息者的userId,查询HistoryChat表,判断是聊过天的用户还是没聊的用户
//                    android.os.Message mess = new android.os.Message();
//                    JSONObject json = new JSONObject(message.toString());
//
//                    readOrNotId = BaseApplication.onlineUserId;//这个人此时开始发送的消息设置为已读
//                    LogUtil.d("test", "readOrNotId:" + readOrNotId + "  发消息人的userId:" + json.getString("from"));
//                    if (readOrNotId.equals(json.getString("from"))) {
//                        mess.what = 99;
//                        mess.obj = json/*.getString("payload")*/;
//                        LogUtil.d("test", handler+"s");
//                        if (handler != null) {
//
//                            handler.sendMessage(mess);
//                        }
//
//                        LogUtil.d("test", "在线发送结束");
//                    } else {//未读信息数量,和最后一条信息,聊天对象toUserId,
//                        mess.what = 44;
//                        mess.obj = json/*.getString("payload")*/;
//                        if (handler2 != null) {
//                            handler2.sendMessage(mess);
//                        }
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
