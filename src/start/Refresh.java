package start;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

import common.statics.PathOfFile;

import databaseutility.OneMatch_add;

public class Refresh extends Thread {
	public void run() {
		try {
			final Path dir = FileSystems.getDefault().getPath(PathOfFile.MATCH_INFO);
			final WatchService watchService = dir.getFileSystem().newWatchService();
			final WatchKey watchKey = dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			while (true) {
				long startTime = System.currentTimeMillis();
				ArrayList<WatchEvent<?>> oneDayEventList = new ArrayList<WatchEvent<?>>(32);
				while ((System.currentTimeMillis() - startTime) < 10) {
					List<WatchEvent<?>> eventList = watchKey.pollEvents();
					if ((eventList != null) && eventList.size() != 0) {
						for (int i = 0; i < eventList.size(); i++) {
							oneDayEventList.add(eventList.get(i));
						}
					}
					watchKey.reset();
				}// 确保一天内的所有比赛信息全部添加完毕
				if (oneDayEventList.size() != 0) {
					for (int i = 0; i < oneDayEventList.size(); i++) {
						WatchEvent<?> event = oneDayEventList.get(i);
						if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
							String matchName = event.context().toString();
							System.out.println(matchName);
							OneMatch_add oneMatch = new OneMatch_add(matchName);
							oneMatch.writeDetailInfoOfPlayerAndTeamToMEN();
							// 第一步应该先将信息写入内存中
							oneMatch.writePlayerPerformToday();// 更新CACHE中的今日球员
							oneMatch.writeTeamNormalInfoToCACHE();// 第二步更新球队的普通数据
							oneMatch.writeTeamHighInfoToCACHE();// 第三步更新球队高级数据
							oneMatch.writePlayerNormalInfoToCACHE();// 第四步更新球员普通数据
							oneMatch.writePlayerHighInfoToCACHE();// 第五步更新球员高级数据//这一步一定是最后做，因为只有有了以上数据才能进行这一步
						}
					}
					System.out.println("一天结束");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
