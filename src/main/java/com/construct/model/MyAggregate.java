package com.construct.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class MyAggregate extends UserDefinedAggregateFunction{

	private StructType inputSchema;
	private StructType bufferSchema;

	public MyAggregate() {
		List<StructField> fields =  new ArrayList<>();
		fields.add(DataTypes.createStructField("inputColumn", DataTypes.LongType, true));
		inputSchema = DataTypes.createStructType(fields);
		
		List<StructField> fields2 =  new ArrayList<>();
		fields2.add(DataTypes.createStructField("count", DataTypes.LongType, true));
		fields2.add(DataTypes.createStructField("sum", DataTypes.LongType, true));
		bufferSchema = DataTypes.createStructType(fields2);
		 
	}

	@Override
	public StructType bufferSchema() {
		return bufferSchema;
	}

	@Override
	public DataType dataType() {
		// TODO Auto-generated method stub
		return DataTypes.DoubleType;
	}

	@Override
	public boolean deterministic() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object evaluate(Row arg0) {
		// TODO Auto-generated method stub
		return ((double) arg0.getLong(0) / arg0.getLong(1));
	}

	@Override
	public void initialize(MutableAggregationBuffer arg0) {
		// TODO Auto-generated method stub
		arg0.update(0,0L);
		arg0.update(1, 0L);
	}

	@Override
	public StructType inputSchema() {
		// TODO Auto-generated method stub
		return inputSchema;
	}

	@Override
	public void merge(MutableAggregationBuffer arg0, Row arg1) {
		arg0.update(0, arg0.getLong(0) + arg1.getLong(0));
		arg0.update(1, arg0.getLong(1) + arg1.getLong(1));
	}

	@Override
	public void update(MutableAggregationBuffer arg0, Row arg1) {
		arg0.update(0, arg0.getLong(0) + arg1.getLong(0));
		arg0.update(1, arg0.getLong(1) + 1);
	}

}
