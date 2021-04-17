package com.imadelfetouh.moderatorservice.dalinterface;

import com.imadelfetouh.moderatorservice.model.dto.UserDTO;
import com.imadelfetouh.moderatorservice.model.response.ResponseModel;

import java.util.List;

public interface UserDal {

    ResponseModel<List<UserDTO>> getUsers();

    ResponseModel<Void> deleteUser(String userId);
}
