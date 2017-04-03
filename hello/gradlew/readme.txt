1.下载gradle 2.2.1,解压，配置系统环境变量，在DOS中输入gradle -v 输出 Gradle 2.2.1，即表示安装成功

2.在eclipse下安装gradle插件,通过
	Window->preference->gradle-arguments->配置插件环境指向JDK目录

3.用记事本打开gradle.properties文件，配置相关属性

4.Run as 选择gradle build...，输入命令 “build line”
		 clean：清理构建；build：执行构建任务；line：统计有效代码行数