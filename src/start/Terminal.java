package start;

import java.io.PrintStream;

import common.statics.Command;

public class Terminal {
	public void excute(PrintStream out, String args[]) {
		int mark = 0;
		while (mark < args.length) {// 确保解析完所有的命令
			if (args[mark].equals(Command.player)) {// 进入球员模块
				mark++;// 1
				if (args[mark].equals(Command.high)) {
					mark++;// 2

				}
				else if (args[mark].equals(Command.hot)) {
					mark++;// 2

				}
				else if (args[mark].equals(Command.king)) {
					mark++;// 2

				}
				else {
					int number = 50;
					if (args[mark].equals(Command.total)) {// 展示数据总和
						mark++;// 2
						if (args[mark].equals(Command.all)) {
							mark++;
						}
						if (args[mark].equals(Command.number)) {
							mark++;
							number = Integer.parseInt(args[mark]);
						}
						// 查找普通数据数量为number，数据类型为总和
						/*
						 * 
						 */
					}
					else {// 展示平均数据
						if (args[mark].equals(Command.average)) {
							mark++;
						}
						if (args[mark].equals(Command.all)) {
							mark++;
						}
						if (args[mark].equals(Command.number)) {
							mark++;
							number = Integer.parseInt(args[mark]);
						}
						// 查找普通数据数量为number，数据类型为平均
						/*
						 * 
						 */
					}
				}
			}
			else if (args[mark].equals(Command.team)) {

			}
		}
	}
}
