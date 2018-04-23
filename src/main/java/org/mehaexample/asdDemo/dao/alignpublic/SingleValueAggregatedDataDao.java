package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mehaexample.asdDemo.model.alignpublic.SingleValueAggregatedData;

import java.util.List;

public class SingleValueAggregatedDataDao {
  private static final String TOTAL_GRADUATED_STUDENTS = "TotalGraduatedStudents";
  private static final String TOTAL_CURRENT_STUDENTS = "TotalCurrentStudents";
  private static final String TOTAL_STUDENTS = "TotalStudents";
  private static final String TOTAL_STUDENTS_DROPPED_OUT = "TotalStudentsDroppedOut";
  private static final String TOTAL_STUDENTS_GOT_JOB = "TotalStudentsGotJob";
  private static final String TOTAL_STUDENTS_IN_BOSTON = "TotalStudentsInBoston";
  private static final String TOTAL_STUDENTS_IN_SEATTLE = "TotalStudentsInSeattle";
  private static final String TOTAL_STUDENTS_IN_SILICON_VALLEY = "TotalStudentsInSiliconValley";
  private static final String TOTAL_STUDENTS_IN_CHARLOTTE = "TotalStudentsInCharlotte";
  private static final String TOTAL_MALE_STUDENTS = "TotalMaleStudents";
  private static final String TOTAL_FEMALE_STUDENTS = "TotalFemaleStudents";
  private static final String TOTAL_FULL_TIME_STUDENTS = "TotalFullTimeStudents";
  private static final String TOTAL_PART_TIME_STUDENTS = "TotalPartTimeStudents";
  private static final String TOTAL_STUDENTS_WITH_SCHOLARSHIP = "TotalStudentsWithScholarship";

  private SessionFactory factory;

  public SingleValueAggregatedDataDao() {
    this.factory = PublicSessionFactory.getFactory();
  }

  public SingleValueAggregatedDataDao(boolean test) {
    if (test) {
      this.factory = PublicTestSessionFactory.getFactory();
    }
  }

  /**
   * Get the total graduated students from the database.
   *
   * @return total graduated students.
   */
  public int getTotalGraduatedStudents() {
    return findValueByKey(TOTAL_GRADUATED_STUDENTS);
  }

  /**
   * Get the total students from the database
   * (including the graduated and dropped off).
   *
   * @return total students.
   */
  public int getTotalStudents() {
    return findValueByKey(TOTAL_STUDENTS);
  }

  /**
   * Get the total current students from the database.
   *
   * @return total current students.
   */
  public int getTotalCurrentStudents() {
    return findValueByKey(TOTAL_CURRENT_STUDENTS);
  }

  /**
   * Get the total students with scholarship from the database.
   *
   * @return total students with scholarship.
   */
  public int getTotalStudentsWithScholarship() {
    return findValueByKey(TOTAL_STUDENTS_WITH_SCHOLARSHIP);
  }

  /**
   * Get the total Male students from the database.
   *
   * @return total Male students.
   */
  public int getTotalMaleStudents() {
    return findValueByKey(TOTAL_MALE_STUDENTS);
  }

  /**
   * Get the total Female students from the database.
   *
   * @return total Female students.
   */
  public int getTotalFemaleStudents() {
    return findValueByKey(TOTAL_FEMALE_STUDENTS);
  }

  /**
   * Get the total Full Time students from the database.
   *
   * @return total Full Time students.
   */
  public int getTotalFullTimeStudents() {
    return findValueByKey(TOTAL_FULL_TIME_STUDENTS);
  }

  /**
   * Get the total Part Time students from the database.
   *
   * @return total Part Time students.
   */
  public int getTotalPartTimeStudents() {
    return findValueByKey(TOTAL_PART_TIME_STUDENTS);
  }

  /**
   * Get the total Dropped Out students from the database.
   *
   * @return total Dropped Out students.
   */
  public int getTotalDroppedOutStudents() {
    return findValueByKey(TOTAL_STUDENTS_DROPPED_OUT);
  }

  /**
   * Get the total students got job from the database.
   *
   * @return total students got job.
   */
  public int getTotalStudentsGotJob() {
    return findValueByKey(TOTAL_STUDENTS_GOT_JOB);
  }

  /**
   * Get the total current students in Boston from the database.
   *
   * @return total current students in Boston.
   */
  public int getTotalStudentsInBoston() {
    return findValueByKey(TOTAL_STUDENTS_IN_BOSTON);
  }

  /**
   * Get the total current students in Seattle from the database.
   *
   * @return total current students in Seattle.
   */
  public int getTotalStudentsInSeattle() {
    return findValueByKey(TOTAL_STUDENTS_IN_SEATTLE);
  }

  /**
   * Get the total current students in Charlotte from the database.
   *
   * @return total current students in Charlotte.
   */
  public int getTotalStudentsInCharlotte() {
    return findValueByKey(TOTAL_STUDENTS_IN_CHARLOTTE);
  }

  /**
   * Get the total current students in Silicon Valley from the database.
   *
   * @return total current students in Silicon Valley.
   */
  public int getTotalStudentsInSiliconValley() {
    return findValueByKey(TOTAL_STUDENTS_IN_SILICON_VALLEY);
  }

  /**
   * Helper class to find the specific value in the database
   * based on the specific analytic key.
   *
   * @param analyticKey analytic search term to find specific value
   *                    from public database.
   * @return specific value based on the specific analytic term.
   */
  private int findValueByKey(String analyticKey) {
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(
              "SELECT analyticValue FROM SingleValueAggregatedData WHERE analyticKey = :analyticKey ");
      query.setParameter("analyticKey", analyticKey);
      return (int) query.list().get(0);
    } finally {
      session.close();
    }
  }

  /**
   * Get the original data format for total graduated students
   * from the public database.
   *
   * @return Single Value Aggregated Data for total grad students.
   */
  public SingleValueAggregatedData findTotalGraduatedStudentsData() {
    return findDataByKey(TOTAL_GRADUATED_STUDENTS);
  }

  /**
   * Get the original data format for total students (including inactive and dropped out)
   * from the public database.
   *
   * @return Single Value Aggregated Data for total students.
   */
  public SingleValueAggregatedData findTotalStudentsData() {
    return findDataByKey(TOTAL_STUDENTS);
  }

  /**
   * Get the original data format for total current students
   * from the public database.
   *
   * @return Single Value Aggregated Data for total current students.
   */
  public SingleValueAggregatedData findTotalCurrentStudentsData() {
    return findDataByKey(TOTAL_CURRENT_STUDENTS);
  }

  /**
   * Get the original data format for total students with scholarship
   * from the public database.
   *
   * @return Single Value Aggregated Data for total students with scholarhip.
   */
  public SingleValueAggregatedData findTotalStudentsWithScholarshipData() {
    return findDataByKey(TOTAL_STUDENTS_WITH_SCHOLARSHIP);
  }

  /**
   * Get the original data format for total male students
   * from the public database.
   *
   * @return Single Value Aggregated Data for total male students.
   */
  public SingleValueAggregatedData findTotalMaleStudentsData() {
    return findDataByKey(TOTAL_MALE_STUDENTS);
  }

  /**
   * Get the original data format for total female students
   * from the public database.
   *
   * @return Single Value Aggregated Data for total female students.
   */
  public SingleValueAggregatedData findTotalFemaleStudentsData() {
    return findDataByKey(TOTAL_FEMALE_STUDENTS);
  }

  /**
   * Get the original data format for total full time students
   * from the public database.
   *
   * @return Single Value Aggregated Data for total full time students.
   */
  public SingleValueAggregatedData findTotalFullTimeStudentsData() {
    return findDataByKey(TOTAL_FULL_TIME_STUDENTS);
  }

  /**
   * Get the original data format for total part time students
   * from the public database.
   *
   * @return Single Value Aggregated Data for total part time students.
   */
  public SingleValueAggregatedData findTotalPartTimeStudentsData() {
    return findDataByKey(TOTAL_PART_TIME_STUDENTS);
  }

  /**
   * Get the original data format for total dropped out students
   * from the public database.
   *
   * @return Single Value Aggregated Data for total dropped out students.
   */
  public SingleValueAggregatedData findTotalDroppedOutStudentsData() {
    return findDataByKey(TOTAL_STUDENTS_DROPPED_OUT);
  }

  /**
   * Get the original data format for total students got job
   * from the public database.
   *
   * @return Single Value Aggregated Data for total students got job.
   */
  public SingleValueAggregatedData findTotalStudentsGotJobData() {
    return findDataByKey(TOTAL_STUDENTS_GOT_JOB);
  }

  /**
   * Get the original data format for total current students in Boston
   * from the public database.
   *
   * @return Single Value Aggregated Data for total current students in Boston.
   */
  public SingleValueAggregatedData findTotalStudentsInBostonData() {
    return findDataByKey(TOTAL_STUDENTS_IN_BOSTON);
  }

  /**
   * Get the original data format for total current students in Seattle
   * from the public database.
   *
   * @return Single Value Aggregated Data for total current students in Seattle.
   */
  public SingleValueAggregatedData findTotalStudentsInSeattleData() {
    return findDataByKey(TOTAL_STUDENTS_IN_SEATTLE);
  }

  /**
   * Get the original data format for total current students in Charlotte
   * from the public database.
   *
   * @return Single Value Aggregated Data for total current students in Charlotte.
   */
  public SingleValueAggregatedData findTotalStudentsInCharlotteData() {
    return findDataByKey(TOTAL_STUDENTS_IN_CHARLOTTE);
  }

  /**
   * Get the original data format for total current students in Silicon Valley
   * from the public database.
   *
   * @return Single Value Aggregated Data for total current students in Silicon Valley.
   */
  public SingleValueAggregatedData findTotalStudentsInSiliconValleyData() {
    return findDataByKey(TOTAL_STUDENTS_IN_SILICON_VALLEY);
  }

  /**
   * Helper class to get the specific original data format for a specific
   * search based on the analytic key.
   *
   * @param analyticKey search term to get specific value.
   * @return single value aggregated data for specific analytic key.
   */
  private SingleValueAggregatedData findDataByKey(String analyticKey) {
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(
              "FROM SingleValueAggregatedData WHERE analyticKey = :analyticKey ");
      query.setParameter("analyticKey", analyticKey);
      List<SingleValueAggregatedData> list = query.list();
      if (list.isEmpty()) {
        return null;
      }
      return list.get(0);
    } finally {
      session.close();
    }
  }

  /**
   * Save or update an updated data for a specific single value aggregated data.
   *
   * @param updatedData the data that will be updated.
   * @return true if saved or updated.
   */
  public synchronized boolean saveOrUpdateData(SingleValueAggregatedData updatedData) {
    Transaction tx = null;
    SingleValueAggregatedData data = findDataByKey(updatedData.getAnalyticKey());
    if (data == null) {
      throw new HibernateException("Data cannot be found.");
    }
    Session session = factory.openSession();
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(updatedData);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }

    return true;
  }
}
