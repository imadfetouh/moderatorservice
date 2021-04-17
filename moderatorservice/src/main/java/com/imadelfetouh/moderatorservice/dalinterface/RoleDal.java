package com.imadelfetouh.moderatorservice.dalinterface;

import com.imadelfetouh.moderatorservice.model.response.ResponseModel;

public interface RoleDal {

    ResponseModel<Void> changeRole(String userId);
}
