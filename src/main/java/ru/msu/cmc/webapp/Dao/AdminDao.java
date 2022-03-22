package ru.msu.cmc.webapp.Dao;

import ru.msu.cmc.webapp.Models.Admin;

public interface AdminDao {
    void createAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(Admin admin);
    Admin getAdminByID(int id);
    Admin getAdminByLogin(String login);
}
