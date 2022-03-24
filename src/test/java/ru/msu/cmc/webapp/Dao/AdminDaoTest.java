package ru.msu.cmc.webapp.Dao;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.msu.cmc.webapp.Dao.Impl.AdminDaoImpl;
import ru.msu.cmc.webapp.Models.Admin;

public class AdminDaoTest {
    private final AdminDao adminDao = new AdminDaoImpl();

    @Test
    public void testCreateAdmin() {
        Admin admin = new Admin("TestCreateAdmin", "test");
        adminDao.createAdmin(admin);
        Assert.assertEquals(admin.getAdmin_login(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_login());
        Assert.assertEquals(admin.getAdmin_password(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_password());
        adminDao.deleteAdmin(admin);
    }

    @Test
    public void testUpdateAdmin() {
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
        Admin admin = new Admin("TestDeleteAdmin", "test");
        adminDao.createAdmin(admin);
        Assert.assertEquals(admin.getAdmin_login(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_login());
        Assert.assertEquals(admin.getAdmin_password(), adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_password());
        adminDao.deleteAdmin(admin);
        Assert.assertNull(adminDao.getAdminByID(admin.getAdmin_id()));
    }

    @Test
    public void testGetAdminByLogin() {
        Admin admin = new Admin("TestGetAdminByLogin", "test");
        adminDao.createAdmin(admin);
        Assert.assertEquals("TestGetAdminByLogin", adminDao.getAdminByID(admin.getAdmin_id()).getAdmin_login());
        adminDao.deleteAdmin(admin);
    }
}
