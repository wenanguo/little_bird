package com.cmtt.base.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HR{


        private Integer status_code;
        private String retStr;


        public HR() {
                this.status_code = HttpStatus.OK.value();
                this.retStr = "";
        }


        public HR(Integer status_code, String retStr) {
                this.status_code = status_code;
                this.retStr = retStr;
        }

        /**
         * 返回成功方法
         * @return
         */
        public static HR ok(){
                return new HR();
        }

        /**
         * 返回失败方法
         * @return
         */
        public static HR err(){

                return new HR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"请求失败");
        }

}
