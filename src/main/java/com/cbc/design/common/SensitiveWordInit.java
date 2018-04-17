package com.cbc.design.common;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * Created by cbc on 2018/4/2.
 */
public class SensitiveWordInit {

    private static final String ENCODING = "GBK";

    private HashMap sensitiveWordMap;

    public Map initKeyWord(){
        try{
            //读取敏感词库
            Set<String> keyWordSet =  readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     *   读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
     * @param keyWordSet
     * @return
     */
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String,String> newWordMap;
        //迭代keyWordSet
        for(String akeyWordSet: keyWordSet){
            key = akeyWordSet;    //关键字
            nowMap = sensitiveWordMap;
            int length = key.length();
            int len = length - 1;
            for(int i = 0 ; i < length; i++){
                char keyChar = key.charAt(i);
                Object wordMap = nowMap.get(keyChar);

                if(wordMap!=null){   //如果存在该key，直接赋值
                    nowMap = (Map)wordMap;
                }else{     //不存在，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWordMap = new HashMap<String,String>();
                    newWordMap.put("isEnd","0");
                    nowMap.put(keyChar,newWordMap);
                    nowMap =  newWordMap;
                }

                if(i == len){
                    nowMap.put("isEnd","1");
                }
            }
        }

    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     * @return
     * @throws IOException 异常
     */
    private Set<String> readSensitiveWordFile() throws IOException {
        Set<String> set;
        String path = "D:/image/txt/all.txt";
        File file = new File(path);
        if (!file.exists()) {
            path = "C:/myproject/image/txt/all.txt";
            file = new File(path);
        }
//        File file = new File("D:\\SensitiveWord.txt");    //读取文件
        try (InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING)) {
            if (file.isFile() && file.exists()) {      //文件流是否存在
                set = new HashSet<>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt;
                while ((txt = bufferedReader.readLine()) != null) {    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else {         //不存在抛出异常信息
                throw new IOException("敏感词库文件不存在");
            }
        }
        return set;
    }
}
