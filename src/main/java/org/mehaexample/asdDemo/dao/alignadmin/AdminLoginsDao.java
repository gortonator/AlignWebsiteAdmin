package org.mehaexample.asdDemo.dao.alignadmin;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mehaexample.asdDemo.model.alignadmin.AdminLogins;

import java.util.List;

public class AdminLoginsDao {
  private static final String ADMIN_EXIST_ERROR = "Admin Login already exist.";

  private SessionFactory factory;
  private Session session;

  /**
   * Default Constructor.
   */
  public AdminLoginsDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    this.factory = AdminSessionFactory.getFactory();
  }

  /**
   * Test constructor.
   *
   * @param test set true to construct a test dao.
   */
  public AdminLoginsDao(boolean test) {
    if (test) {
      this.factory = AdminTestSessionFactory.getFactory();
    }
  }

  /**
   * Find and admin logins by email.
   *
   * @param email email to be found.
   * @return admin logins object if found; null otherwise.
   */
  public AdminLogins findAdminLoginsByEmail(String email) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM AdminLogins WHERE email = :email");
      query.setParameter("email", email);
      List list = query.list();
      if (list.isEmpty()) {
        return null;
      }
      return (AdminLogins) list.get(0);
    } finally {
      session.close();
    }
  }

  /**
   * Create a new Admin Login. The Administrator has to be created
   * first in the Admin Database before creating the login. Otherwise,
   * it will fail.
   *
   * @param adminLogin new Admin Login object to be created; Admin should
   *                   be created before creating this admin login.
   * @return new admin login.
   * @throws HibernateException if email based on the Administrator has
   *                            not been created yet, or there is something wrong
   *                            with the Database connection.
   */
  public AdminLogins createAdminLogin(AdminLogins adminLogin) {
    Transaction tx = null;
    if (findAdminLoginsByEmail(adminLogin.getEmail()) != null) {
      throw new HibernateException(ADMIN_EXIST_ERROR);
    } else {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.save(adminLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    }
    return adminLogin;
  }

  /**
   * Update the admin login. This method will update
   * the login based on the email of the admin login param.
   *
   * @param adminLogin updated admin login.
   * @return true if updated, false otherwise.
   */
  public boolean updateAdminLogin(AdminLogins adminLogin) {
    Transaction tx = null;
    if (findAdminLoginsByEmail(adminLogin.getEmail()) != null) {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.saveOrUpdate(adminLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Admin Login with email: " + adminLogin.getEmail() +
              " not found.");
    }
    return true;
  }

  /**
   * Delete an admin login by their email.
   *
   * @param email for the admin login to be deleted.
   * @return true if deleted, false otherwise.
   */
  public boolean deleteAdminLogin(String email) {
    AdminLogins adminLogin = findAdminLoginsByEmail(email);
    if (adminLogin != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(adminLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Admin Login does not exist.");
    }
    return true;
  }
}