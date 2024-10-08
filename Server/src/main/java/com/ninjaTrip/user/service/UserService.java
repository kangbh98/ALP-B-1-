package com.ninjaTrip.user.service;

import com.ninjaTrip.user.dto.User;

import java.sql.SQLException;

public interface UserService {
    void createUser(User user) throws SQLException;
    int loginUser(String email, String password) throws SQLException;
    User getUserByUserIdx(int userIdx) throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUser(int userIdx) throws SQLException;
    void saveRefreshToken(int userIdx, String refreshToken) throws SQLException;
    Object getRefreshToken(int userIdx) throws SQLException;
    void deleteRefreshToken(int userIdx) throws SQLException;
}
