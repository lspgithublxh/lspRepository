package com.construct.mapreduce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.StringUtils;

public class NWordCount2 {

	public static class  NMaper extends Mapper<Object, Text, Text, IntWritable>{
		
		private final Text text = new Text();
		private final IntWritable num = new IntWritable(1);
		
		private boolean caseSensitive;
		private Set<String> filter = new HashSet<String>();
		
		@Override
		protected void setup(Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			Configuration conf = context.getConfiguration();
			caseSensitive = conf.getBoolean("wordcount.case.sensitive", true);
			if(conf.getBoolean("wordcount.skip.patterns", false)) {
				URI[] cacheFiles = Job.getInstance(conf).getCacheFiles();
				for(URI uri : cacheFiles) {
					Path path = new Path(uri.getPath());
					String file = path.getName().toString();
					parseSkipFile(file);
				}
			}
		}

		private void parseSkipFile(String file) throws FileNotFoundException {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String pattern = null;
			try {
				while((pattern = reader.readLine()) != null) {
					filter.add(pattern);
				}
			} catch (IOException e) {
				System.out.println("parse pattern.txt failed: " + StringUtils.stringifyException(e));
			}
		}
		
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String line = (caseSensitive ? value.toString() : value.toString().toLowerCase());
			for(String pattern : filter) {
				line = line.replaceAll(pattern, "");
			}
			StringTokenizer token = new StringTokenizer(line);
			while(token.hasMoreTokens()) {
				text.set(token.nextToken());
				context.write(text, num);
				Counter counter = context.getCounter("CountersEnum.class.getName()", "CountersEnum.INPUT_WORDS.toString()");
				System.out.println("counter:-==============" + counter);
			}
		}
	}
	
	public static class NReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		
		private final IntWritable num = new IntWritable();
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> iterator,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable num : iterator) {
				sum += num.get();
			}
			num.set(sum);
			context.write(key, num);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		GenericOptionsParser parser = new GenericOptionsParser(conf, args);
		String[] remainArgs = parser.getRemainingArgs();
//		if((remainArgs.length != 3) && (remainArgs.length != 6)) {
//			System.out.println(" params num is not right!");
//			System.exit(1);
//		}
		Job job = Job.getInstance(conf, "NWordCount2");
		job.setJarByClass(NWordCount2.class);
		job.setMapperClass(NMaper.class);
		job.setCombinerClass(NReducer.class);
		job.setReducerClass(NReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		List<String> otherArgs = new ArrayList<String>();
		for(int i = 0 ; i < remainArgs.length; i++) {
			if("-skip".equals(remainArgs[i])) {
				job.addCacheFile(new Path(remainArgs[++i]).toUri());
				job.getConfiguration().setBoolean("wordcount.skip.patterns", true);
			}else if(remainArgs[i].startsWith("/")){
				otherArgs.add(remainArgs[i]);
			}			
		}
		
		FileInputFormat.addInputPath(job, new Path(otherArgs.get(1)));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(2)));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
		
	}
}
