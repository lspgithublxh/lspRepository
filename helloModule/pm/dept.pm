<?xml version="1.0" encoding="UTF-8"?>
<app-data>
<database name="db" package="null" defaultIdMethod="null" baseClass="BaseObject" basePeer="BasePeer">
<table name="dept" id="4028f9c6582d2d2d01582d2d2d1d0000" javaName="Dept" idMethod="uuid.hex" isLinkTable="false" sequenceName="" pkg="dept" skipSql="false" abstract="false" chineseDescription="dept" generatePO="true" lazy="true" generatePK="true" generateFK="true">
    <meta key="height" value="280" />
    <meta key="width" value="250" />
    <meta key="xpos" value="0" />
    <meta key="ypos" value="0" />
    <column name="id" id="4028f9c6582d2d2d01582d2d2d1d0001" javaName="id" description="车辆标识" primaryKey="true" required="true" type="VARCHAR" size="32" editorType="text" default="" inputSize="80" order="false" />
    <column name="name" id="4028f9c6582d2d2d01582d2d2d5b0008" javaName="name" description="车辆名称" required="false" type="VARCHAR" size="32" editorType="text" default="" inputSize="80" order="false" />
    <column name="period_of_validity" id="4028f9c6582d2d2d01582d2d2d5b0009" javaName="periodOfValidity" description="有效期" required="false" type="INTEGER" editorType="text" default="10" inputSize="80" order="false" />
    <column name="color" id="4028f9c6582d2d2d01582d2d2d5b000a" javaName="color" description="车辆颜色" required="false" type="VARCHAR" size="255" editorType="text" default="" inputSize="80" order="false" />
</table>
</database>
</app-data>