package com.bitech.joke.http.converter;

import com.bitech.joke.utils.Logger;
import com.bitech.joke.utils.Rxbus;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * <p></p>
 * Created on 2016/4/19 10:37.
 *
 * @author Lucy
 */
public class CustomResponseBodyConverter<T>  implements Converter<ResponseBody,T>{

    public static Logger logger=Logger.getLogger();

    private final TypeAdapter<T> typeAdapter;

    public CustomResponseBodyConverter(TypeAdapter<T> typeAdapter){
        this.typeAdapter=typeAdapter;
    }
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response=value.string();
        logger.i("---------response转换--------");
        logger.i("---response:"+response);

        try{
            JSONObject responseJson=new JSONObject(response);
            int errorCode=responseJson.getInt("error_code");
            if(errorCode==0){//请求返回成功
                String result=responseJson.getString("result");
                return typeAdapter.fromJson(result);
            }else{//请求返回失败
                String errorMessage=responseJson.getString("reason");
                logger.e("------请求失败："+errorMessage+"---------");
                Rxbus.getInstance().post("convert",errorMessage);
            }
        }catch (Exception e){
            logger.e("-------解析异常--------");
            e.printStackTrace();
        }

        return null;
    }
}
