package Grading_System.service;

import Grading_System.dao.UserDAO;
import Grading_System.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public String hashPass(String pass){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verify(String userName, String password){
        User user = userDAO.getUserByName(userName);
        if (user != null && user.getPassword().equals(password))
            return true;
        return false;
    }

    public User getUserByName(String userName){
        return userDAO.getUserByName(userName);
    }

    public String getUserRole(int userId){
        return userDAO.getUserById(userId).getRole();
    }
}
