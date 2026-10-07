package com.gamelog.gamelog;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
public class CdkeyController {
    private final CdkeyMapper cdkeyMapper;

    public CdkeyController(CdkeyMapper cdkeyMapper) {
        this.cdkeyMapper = cdkeyMapper;
    }

    @GetMapping("/cdkeys")
    public Result<List<Cdkey>> getCdkeys() {
        List<Cdkey> list = cdkeyMapper.findAll();
        return Result.success(list);
    }

    @GetMapping("/cdkeys/{id}")
    public Result<Cdkey> getCdkeyById(@PathVariable Long id) {
        Cdkey cdkey = cdkeyMapper.findById(id);
        if (cdkey == null) {
            throw new BizException(404, "CDKey不存在");
        }
        return Result.success(cdkey);
    }

    @PostMapping("/cdkeys")
    public Result<Cdkey> addCdkey(@RequestBody Cdkey cdkey) {
        cdkeyMapper.insert(cdkey);
        Cdkey addedCdkey = cdkeyMapper.findById(cdkey.getId());
        return Result.success(addedCdkey);
    }

    @PostMapping("/cdkeys/{id}/buy")
    public Result<Cdkey> buyCdkey(@PathVariable Long id) throws InterruptedException {
        Cdkey cdkey = cdkeyMapper.findById(id);
        if (cdkey == null) {
            throw new BizException(404, "CDKey不存在");
        }
        int updatedRows = cdkeyMapper.updateStatus(id);
        if (updatedRows == 0) {
            throw new BizException(409, "该 key 已被购买");
        }
        Cdkey updatedCdkey = cdkeyMapper.findById(id);
        return Result.success(updatedCdkey);
    }

    @DeleteMapping("/cdkeys/{id}")
    public Result<Void> deleteCdkey(@PathVariable Long id) {
        int deletedRows = cdkeyMapper.delete(id);
        if (deletedRows == 0) {
            throw new BizException(404, "CDKey不存在");
        }
        return Result.success(null);
    }
}
