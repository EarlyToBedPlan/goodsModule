package cn.edu.xmu.goods.model.bo;

/**
 * @author Yancheng Lai
 * createdBy Yancheng Lai 2020/12/7 18:56
 * modifiedBy Yancheng Lai 18:56
 **/

import cn.edu.xmu.ooad.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor

public class Spec  {

    private long id;

    private String name;

     List<SpecItems> specItems;

    // SpecItems it;

    public Spec(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(jsonString);
            specItems = new ArrayList<>();
            List<JsonNode> list1=root.findValues("id");
            List<JsonNode> list2=root.findValues("name");
            for (Integer i = 0;i < list1.size();i++){
                specItems.add(new SpecItems(list1.get(i).asInt(),list2.get(i).toString()));

            name = JacksonUtil.parseString(jsonString,"name");
            id = JacksonUtil.parseInteger(jsonString,"id");
        }


    }

    public Spec (){}

}

