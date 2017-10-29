package com.construct.mapreduce;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class NWordCount {

	public static class CMap extends Mapper<Object, Text, Text, IntWritable>{
		
		private final Text text = new Text();
		private final IntWritable num = new IntWritable(1);
		
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			StringTokenizer toker = new StringTokenizer(value.toString());
			while(toker.hasMoreTokens()) {
				text.set(toker.nextToken());
				context.write(text, num);
			}
		}
	}
	
	public static class CReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
		
		private final IntWritable num = new IntWritable();
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> iterator,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable value : iterator) {
				sum += value.get();
			}
			num.set(sum);
			context.write(key, num);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		System.out.println(args[0] + ",--------" + args[1]);
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "lishaoping_job_name");
		job.setJarByClass(NWordCount.class);
		job.setMapperClass(CMap.class);
		job.setCombinerClass(CReducer.class);
		job.setReducerClass(CReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[1]));//remain参数不同
		
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
