package com.zouxxyy.mr.combiner;

import com.zouxxyy.mr.wordcount.WcMapper;
import com.zouxxyy.mr.wordcount.WcReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * 测试Combiner
 */

public class WcDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        args = new String[] { "data/input/wordCount", "data/output" };

        // 1 获取Job对象
        Job job = Job.getInstance(new Configuration());

        // 2 设置类路径
        job.setJarByClass(com.zouxxyy.mr.wordcount.WcDriver.class);

        // 3 关联Map和Reduce类
        job.setMapperClass(WcMapper.class);
        job.setReducerClass(WcReducer.class);

        // 4 设置Mapper输出的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 设置combiner, 和reducer的类一样
        job.setCombinerClass(WcReducer.class);

        // 5 设置数据输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 6. 设置输入输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean b = job.waitForCompletion(true);
        System.exit(b  ? 0 : 1);
    }
}

