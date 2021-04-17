package com.imadelfetouh.moderatorservice.endpoint;

import com.imadelfetouh.moderatorservice.dalinterface.UserDal;
import com.imadelfetouh.moderatorservice.model.dto.UserDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;
import com.imadelfetouh.moderatorservice.model.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserResource {

    @Autowired
    private UserDal userDal;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getUsers() {
        ResponseModel<List<UserDTO>> responseModel = userDal.getUsers();

        if(responseModel.getResponseType().equals(ResponseType.EMPTY)) {
            return ResponseEntity.noContent().build();
        }
        else if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().body(responseModel.getData());
        }

        return ResponseEntity.status(500).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {

        ResponseModel<Void> responseModel = userDal.deleteUser(userId);

        if(responseModel.getResponseType().equals(ResponseType.CORRECT)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(500).build();

    }
}
