package com.example.pinyintest;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XmlUtils {
    public static void main(String[] args) {
        //readXmlByDom4j();
//        Field[] declaredFields = User.class.getDeclaredFields();
//        for (Field f:declaredFields
//             ) {
//            System.out.println(f.getName());
//        }



        Hobby b = new Hobby(1, "篮球");
        Hobby s = new Hobby(2, "游泳");
        Hobby f = new Hobby(3, "足球");

        List<User> userList=new LinkedList<>();
        List<Hobby> hobbyList=new ArrayList<>();
        hobbyList.add(b);
        hobbyList.add(s);
        hobbyList.add(f);

        User user0 = new User(1,"李1",23,"man","12345");
        user0.setHobbys(hobbyList);
        User user1 = new User(2,"李2",24,"women","12345");
        user1.setHobbys(hobbyList);
        User user2 = new User(3,"李3",25,"man","12345");
        user2.setHobbys(hobbyList);
        User user3 = new User(4,"李4",26,"man","1238");
        user3.setHobbys(hobbyList);
        User user4 = new User(5,"李5",27,"women","1123");
        user4.setHobbys(hobbyList);
        userList.add(user0);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        createXmlByDom4j(userList);
        //System.out.println(User.class.getSimpleName());//User
    }


    public static void readXmlByDom4j(){
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(new File("C:\\Users\\Administrator\\Desktop\\new.xml"));

            //获取document下面根节点
            Element rootElement = doc.getRootElement();
            //判断是否是根节点
            if (rootElement.isRootElement()){
                readAllChildNodes(rootElement);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void readAllChildNodes(Element rootElement) {
        List<Element> elements = rootElement.elements();
        if (elements.size()>0){
            for (Element e:elements
                 ) {
                readAllChildNodes(e);
            }
        }else {
            System.out.println(rootElement.getNodeTypeName()+"->"+rootElement.getName()+"->"+rootElement.getStringValue());
        }
    }

    public static<T> void createXmlByDom4j(List<T> userList){



        //创建document对象
        Document doc = DocumentHelper.createDocument();
        doc.addComment("这里是注释");
        //创建根元素
        Element userInfo = doc.addElement("UserInfo");



        for (int i=0;i<userList.size();i++){
            User user = (User) userList.get(i);
            //创建子节点

            Element userNode = userInfo.addElement("User");

            //给节点设置属性
            userNode.addAttribute("id","101"+i);

            List<String> userNameList = reflection(user.getClass());

            if (userNameList.isEmpty()){
                return;
            }
            for (String is:userNameList
                 ) {
                userNode.addElement(is);
            }

            Element uid = userNode.element("uid");
            Element userName = userNode.element("userName");
            Element age = userNode.element("age");
            Element sex = userNode.element("sex");
            Element password = userNode.element("password");

            uid.setText(user.getUid().toString());
            userName.setText(user.getUserName());
            age.setText(user.getAge().toString());
            sex.setText(user.getSex());
            password.setText(user.getPassword());

            List<Hobby> hobbyis = user.getHobbys();


            List<String> hobbyName = reflection(Hobby.class);
            Element hobbys = userNode.element("hobbys");
            for (Hobby item:hobbyis
                 ) {

                Element hobby = hobbys.addElement("hobby");

                for (String ss:hobbyName
                     ) {
                    hobby.addElement(ss);
                }
                Element id = hobby.element("id");
                Element name = hobby.element("name");
                id.setText(item.getId().toString());
                name.setText(item.getName());
            }
        }

        OutputFormat prettyPrint = OutputFormat.createPrettyPrint();
        prettyPrint.setEncoding("utf-8");
        try {
            Writer writer=new FileWriter("C:\\Users\\Administrator\\Desktop\\User.xml");
            XMLWriter xmlWriter = new XMLWriter(writer, prettyPrint);
            xmlWriter.write(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static<T> List<String> reflection(Class<T> clazz) {
        List<String> nameList=new LinkedList<>();
        try {

            if (clazz.isInstance(new User())){
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field f:declaredFields
                        ) {
                    String name1 = f.getName();
                    nameList.add(name1);
                }
            }else if (clazz.isInstance(new Hobby())){

                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field f:declaredFields
                        ) {
                    String name1 = f.getName();
                    nameList.add(name1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nameList;
    }
}
