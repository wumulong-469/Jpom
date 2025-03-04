/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Code Technology Studio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.dromara.jpom.service.system;

import cn.hutool.core.util.ReflectUtil;
import cn.keepbx.jpom.model.BaseJsonModel;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dromara.jpom.model.data.SystemParametersModel;
import org.dromara.jpom.service.h2db.BaseDbService;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @author bwcx_jzy
 * @since 2021/12/2
 */
@Service
@Slf4j
public class SystemParametersServer extends BaseDbService<SystemParametersModel> {


    /**
     * 先尝试更新，更新失败尝试插入
     *
     * @param name      参数名称
     * @param jsonModel 参数值
     * @param desc      描述
     */
    public void upsert(String name, BaseJsonModel jsonModel, String desc) {
        SystemParametersModel systemParametersModel = new SystemParametersModel();
        systemParametersModel.setId(name);
        systemParametersModel.setValue(jsonModel.toJson().toString());
        systemParametersModel.setDescription(desc);
        super.upsert(systemParametersModel);
    }

    /**
     * 先尝试更新，更新失败尝试插入
     *
     * @param name 参数名称
     * @param data 参数值
     * @param desc 描述
     */
    public void upsert(String name, Object data, String desc) {
        SystemParametersModel systemParametersModel = new SystemParametersModel();
        systemParametersModel.setId(name);
        systemParametersModel.setValue(JSONObject.toJSONString(data));
        systemParametersModel.setDescription(desc);
        super.upsert(systemParametersModel);
    }

    /**
     * 查询 系统参数 值
     *
     * @param name 参数名称
     * @param cls  类
     * @param <T>  泛型
     * @return data
     */
    public <T> T getConfig(String name, Class<T> cls) {
        return this.getConfig(name, cls, null);
    }

    /**
     * 查询 系统参数 值
     *
     * @param name  参数名称
     * @param cls   类
     * @param mapTo 回调
     * @param <T>   泛型
     * @return data
     */
    public <T> T getConfig(String name, Class<T> cls, Function<T, T> mapTo) {
        SystemParametersModel parametersModel = super.getByKey(name);
        if (parametersModel == null) {
            return null;
        }
        T jsonToBean = parametersModel.jsonToBean(cls);
        if (mapTo == null) {
            return jsonToBean;
        }
        return mapTo.apply(jsonToBean);
    }

    /**
     * 查询系统参数值,没有数据创建一个空对象
     *
     * @param name 参数名称
     * @param cls  类
     * @param <T>  泛型
     * @return data
     */
    public <T> T getConfigDefNewInstance(String name, Class<T> cls) {
        T config;
        try {
            config = this.getConfig(name, cls);
        } catch (Exception e) {
            log.error("读取系统参数异常", e);
            return ReflectUtil.newInstance(cls);
        }
        return config == null ? ReflectUtil.newInstance(cls) : config;
    }
}
