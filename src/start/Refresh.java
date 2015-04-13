package start;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;

import common.statics.PathOfFile;
import databaseutility.OneMatch_add;

public class Refresh extends Thread {
	public void run() {
		try {
			final Path dir = FileSystems.getDefault().getPath(PathOfFile.MATCH_INFO);
			final WatchService watchService = dir.getFileSystem().newWatchService();
			final WatchKey watchKey = dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			while (true) {
				String matchName;
				ArrayList<OneMatch_add> matches = new ArrayList<OneMatch_add>(16);
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						matchName = event.context().toString();
						OneMatch_add oneMatch = new OneMatch_add(matchName);
						matches.add(oneMatch);
						oneMatch.writeDetailInfoOfPlayerAndTeamToMEN();
						oneMatch.writeTeamNormalInfoToCACHE();
						oneMatch.writeTeamHighInfoToCACHE();
						oneMatch.writePlayerNormalInfoToCACHE();
					}
				}
				for (int i = 0; i < matches.size(); i++) {
					matches.get(i).writePlayerHighInfoToCACHE();
					matches.get(i).writeTeamHighInfoToCACHE();
				}
				if (!watchKey.reset()) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
