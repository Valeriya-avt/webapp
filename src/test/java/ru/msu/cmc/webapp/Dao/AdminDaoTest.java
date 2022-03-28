package ru.msu.cmc.webapp.Dao;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.msu.cmc.webapp.Dao.Impl.AdminDaoImpl;
import ru.msu.cmc.webapp.Models.Admin;

public class AdminDaoTest {

    @Test
    public void testCreateAdmin() {
        AdminDao adminDao = new AdminDaoImpl();
        Admin admin = new Admin("TestCreateAdmin", "test");
        adminDao.createAdmin(admin);
        Assert.assertEquals(admin.getAdmin_login(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_login());
        Assert.assertEquals(admin.getAdmin_password(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_password());
        adminDao.deleteAdmin(admin);
    }

    @Test
    public void testUpdateAdmin() {
        AdminDao adminDao = new AdminDaoImpl();
        Admin admin = new Admin("TestUpdateAdmin", "test");
        adminDao.createAdmin(admin);
        Assert.assertEquals(admin.getAdmin_login(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_login());
        admin.setAdmin_login("NewTestUpdateAdmin");
        adminDao.updateAdmin(admin);
        Assert.assertEquals(admin.getAdmin_login(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_login());
        adminDao.deleteAdmin(admin);
    }

    @Test
    public void testDeleteAdmin() {
        AdminDao adminDao = new AdminDaoImpl();
        Admin admin = new Admin("TestDeleteAdmin", "test");
        adminDao.createAdmin(admin);
        Assert.assertEquals(admin.getAdmin_login(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_login());
        Assert.assertEquals(admin.getAdmin_password(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_password());
        adminDao.deleteAdmin(admin);
        Assert.assertNull(adminDao.getAdminByID(admin.getAdmin_id()));
    }

    @Test
    public void testGetAdminByLogin() {
        AdminDao adminDao = new AdminDaoImpl();
        Admin new_admin = new Admin("TestGetAdminByLogin", "akula");
        adminDao.createAdmin(new_admin);
        Admin check_admin = adminDao.getAdminByLogin("TestGetAdminByLogin");
        Assert.assertEquals("TestGetAdminByLogin", check_admin.getAdmin_login());
        adminDao.deleteAdmin(new_admin);
    }
}
