package com.bcx.plat.core.utils;
/**
 * 符号转换类
 * @author Administrator
 *
 */
public class SymbolChange {
	public static String Symbol(String s){
		if(s==null){
			s="";
		}
		String str = s.trim().replaceAll("[' ']+"," ");
		String strChange = "";
		String change = "";
		if(str.contains(",") && str.contains("，")){
			change = str.replace(",", " ");
			strChange = change.replace("，", " ");
		}else if(str.contains(",")){
			strChange = str.replace(",", " ");
		}else if(str.contains("，")){
			strChange = str.replace("，", " ");
		}else if(str.contains(" ")){
			strChange = str.replace(" ", " ");
		}else{
			strChange=str;
		}
		return strChange;
	}
}
