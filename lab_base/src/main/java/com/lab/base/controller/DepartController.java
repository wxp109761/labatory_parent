package com.lab.base.controller;

import com.lab.base.pojo.Depart;
import com.lab.base.service.DepartService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/depart")
@CrossOrigin
public class DepartController {

    @Autowired
    private DepartService departService;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){


        Map<String,Object> map=new HashMap<>();
        map.put("departList",departService.findAll());
        return new Result(true, StatusCode.OK,"查询成功",map);
    }

    @RequestMapping(value = "/{departId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("departId") String departId){
        //System.out.println(request.getHeader("Authorization"));


        return new Result(true, StatusCode.OK,"查询成功",departService.findById(departId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Depart depart){
        departService.save(depart);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{departId}",method = RequestMethod.PUT)
    public Result update(@PathVariable("departId")String departId,@RequestBody Depart depart){
        depart.setDepart_id(departId);
        departService.update(depart);
        return new Result(true,StatusCode.OK,"更新成功");
    }

    /**
     * 删除
     * @param departId
     * @return
     */
    @RequestMapping(value = "/{departId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable("departId")String departId){
        departService.deleteById(departId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
