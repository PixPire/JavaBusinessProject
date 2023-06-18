package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.UserGroup;

import java.util.Optional;

public interface UserGroupDao extends AbstractDao<UserGroup> {
    public UserGroup findUserGroupByName(String userGroupName);
}
