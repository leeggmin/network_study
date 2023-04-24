package day0424;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;

public class CreateJsonExample {

    public static void main(String[] args) throws IOException {
        
        //JSON 객체 생성
        JSONObject root = new JSONObject();
        
        //속성 추가
        root.put("id", "winter");
        root.put("name", "한겨울");
        root.put("age", 15);
        root.put("student", true);

        JSONObject tel = new JSONObject();

        //객체 속성 추가
        tel.put("home", "02-123-4567");
        tel.put("mobile", "010-1234-5678");
        root.put("tel", tel);

        //배열 속성 추가
        JSONArray skill = new JSONArray();
        skill.put("JAVA");
        skill.put("C");
        skill.put("C++");
        root.put("skill", skill);

        //JSON 얻기
        String json = root.toString();

        //콘솔에 출력
        System.out.println(json);
        
        //파일로 저장
        Writer writer = new FileWriter("/Users/jimin/Desktop/member.json", Charset.forName("UTF-8"));
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
