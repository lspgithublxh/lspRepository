/**
 * 
 */
package com.hadoop.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * @author lishaoping
 * 2017年5月22日下午10:01:16
 * WordsCount
 */
public class WordsCount {

	public static class MyMapper extends Mapper<Object, Text, Text, IntWritable>{
		private final static IntWritable one = new IntWritable();
		private Text words = new Text();
		
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException{
			int idx = value.toString().indexOf(" ");
			if(idx > 0){
				String e = value.toString().substring(0, idx);
				words.set(e);
				context.write(value, one);
			}
		}
		
	}
	
	public static class MyReducer extends Reducer<Object, IntWritable, Text, IntWritable>{
		private IntWritable result = new IntWritable();
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException{
			int sum = 0;
			for(IntWritable val : values){
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
		
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
		if(otherArgs.length < 2 ){
			System.out.println("参数少了");
			System.exit(2);
		}
		Job job = Job.getInstance(conf, WordsCount.class.getName() );
		job.setJarByClass(WordsCount.class);
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		job.setCombinerClass(MyReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
