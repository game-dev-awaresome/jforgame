package com.kingston;

import com.kingston.db.DbService;
import com.kingston.game.ConfigDatasPool;
import com.kingston.game.player.PlayerManager;
import com.kingston.game.player.model.Player;
import com.kingston.logs.LoggerUtils;
import com.kingston.net.MessageFactory;
import com.kingston.net.SocketServer;
import com.kingston.net.context.TaskHandlerContext;
import com.kingston.orm.OrmProcessor;
import com.kingston.orm.utils.DbUtils;

public class ServerStarter {

	public static void main(String args[]) {
		//初始化协议池
		MessageFactory.INSTANCE.initMeesagePool();
		//读取服务器配置
		ServerConfig.getInstance().initFromConfigFile();
		//初始化orm框架
		OrmProcessor.INSTANCE.initOrmBridges();
		//初始化消息工作线程池
		TaskHandlerContext.INSTANCE.initialize();
		//初始化数据库连接池
		DbUtils.init();
		//读取所有策划配置
		ConfigDatasPool.getInstance().loadAllConfigs();
		//异步持久化服务
		DbService.getInstance().init();
		
		Player player = PlayerManager.getInstance().get(10000L);
		System.err.println(player);
		 player = PlayerManager.getInstance().get(10000L);
		 System.err.println(player);
		//启动socket服务
		try{
			new SocketServer().start();
		}catch(Exception e) {
			LoggerUtils.error("ServerStarter failed ", e);
		}
	} 

}
