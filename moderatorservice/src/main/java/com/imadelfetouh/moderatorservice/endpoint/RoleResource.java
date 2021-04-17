package com.imadelfetouh.moderatorservice.endpoint;

import com.imadelfetouh.moderatorservice.dalinterface.RoleDal;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
public class RoleResource {

    @Autowired
    private RoleDal roleDal;

    @PutMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {

        ResponseModel<Void> responseModel = roleDal.changeRole(userId);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();

    }
}
