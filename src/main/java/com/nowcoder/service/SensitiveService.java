package com.nowcoder.service;

import com.nowcoder.controller.HomeController;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
/*
敏感词过滤
 */
public class SensitiveService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    private TrieNode rootNode = new TrieNode();
    public void addWord(String lineTxt){
        TrieNode tempNode = rootNode;
        for(int i = 0;i < lineTxt.length();i++){
            Character c = lineTxt.charAt(i);
            if(isSymbol(c)){
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if(node == null){
                tempNode.addSubNode(c,new TrieNode());
            }
            tempNode = tempNode.getSubNode(c);
            if(i == lineTxt.length() - 1){
             //   System.out.println(tempNode.subNodes.keySet());
                tempNode.setKeyWordEnd(true);
            }
         //   tempNode = tempNode.getSubNode(c);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader read = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                addWord(lineTxt.trim());
            }
            read.close();
        }catch (Exception e){
            logger.error("读取敏感词文件失败" + e.getMessage());
        }
    }

    public class TrieNode{
        private boolean end = false;

        private Map<Character,TrieNode>subNodes = new HashMap<>();

        public void addSubNode(Character key,TrieNode node){
            subNodes.put(key,node);
        }

        public TrieNode getSubNode(Character key){
            return subNodes.get(key);
        }

        boolean isKeyWordEnd(){
            return end;
        }

        void setKeyWordEnd(boolean end){
            this.end = end;
        }
    }

    public boolean isSymbol(char c){
        int ic = (int)c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);//不是英文并且不是东亚文字，则是符号
    }
    public String filter(String text){
        if(StringUtils.isBlank(text)){
            return text;
        }
        StringBuilder result = new StringBuilder();
        String replacement = "***";
        int begin = 0,position = 0;
        TrieNode tempNode = rootNode;
        while(position < text.length()){
            Character c = text.charAt(position);
            if(isSymbol(c)){
                if(tempNode == rootNode){
                    result.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
           // if(node != null)
          //  System.out.println(position + " " + node.subNodes.keySet() + " " + node.end);
            if(tempNode == null){
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            }else if(tempNode.isKeyWordEnd()){
                result.append(replacement);
                begin = position + 1;
                position = begin;
                tempNode = rootNode;
            }else{
                position++;
            }
        }
        return result.toString();
    }

//    public static void main(String[] args) {
//        SensitiveService s = new SensitiveService();
//        s.addWord("色情");
//        s.addWord("赌博");
//        System.out.println(s.filter("hi  你好色  情 ++   赌 博"));
//    }
}
