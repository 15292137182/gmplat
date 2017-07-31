/**
 * <p>Title: SequenceProducer</p>
 * <p>Description: product sequence</p>
 * <p>Copyright: Baosight Software LTD.co Copyright (c) 2016</p>
 * <p>Company: Baosight Software</p>
 * @author Huang Zhongwei
 * @version 1.0
 * <pre>Histroy:
 *       2016-9-20  Huang Zhongwei  Create
 *</pre>
*/
package com.bcx.plat.core.utils;

import com.bcx.plat.gmplat.sqlmapper.SequenceMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Scope("singleton")
public class SequenceProducer {

	@Resource
	private SequenceMapper sequenceMapper;
	
	//获取当前最大版本号值
	public int currval(String name){

		int num= sequenceMapper.currval(name);
		return num;
	}
	//获取版本最大值+0.1，赋予新增的版本号
	public synchronized int nextval(String name){
		
		int num= sequenceMapper.getNextval(name);
		sequenceMapper.nextval(name);
		return num;
}
}

