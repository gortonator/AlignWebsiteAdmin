package org.mehaexample.asdDemo.dao.alignadmin;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mehaexample.asdDemo.dao.alignprivate.StudentSessionFactory;
import org.mehaexample.asdDemo.dao.alignprivate.StudentTestSessionFactory;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;

import java.util.ArrayList;
import java.util.List;

public class GenderRatioDao {
  private SessionFactory factory;

  /**
   * Default constructor.
   */
  public GenderRatioDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    this.factory = StudentSessionFactory.getFactory();
  }

  /**
   * Test constructor.
   *
   * @param test set true to construct test dao.
   */
  public GenderRatioDao(boolean test) {
    if (test) {
      this.factory = StudentTestSessionFactory.getFactory();
    }
  }

  /**
   * Get the gender ratio broke down in years based on the Campus Location.
   *
   * @param campuses to broke down the annual gender ratio.
   * @return list of gender ratio.
   */
  public List<GenderRatio> getYearlyGenderRatio(List<Campus> campuses) {
    String hql = "SELECT NEW org.mehaexample.asdDemo.model.alignadmin.GenderRatio(s.entryYear, " +
            "COUNT(CASE s.gender WHEN 'M' THEN 1 ELSE NULL END), " +
            "COUNT(CASE s.gender WHEN 'F' THEN 1 ELSE NULL END)) " +
            "FROM Students s " +
            "WHERE s.campus IN (:campuses) " +
            "GROUP BY s.entryYear " +
            "ORDER BY s.entryYear ASC ";
    List<GenderRatio> list;
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(hql);
      query.setParameter("campuses", campuses);
      list = query.list();
    } finally {
      session.close();
    }
    return list;
  }
}
