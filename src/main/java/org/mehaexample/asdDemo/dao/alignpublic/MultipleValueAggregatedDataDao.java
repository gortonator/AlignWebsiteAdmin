package org.mehaexample.asdDemo.dao.alignpublic;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mehaexample.asdDemo.model.alignpublic.DataCount;
import org.mehaexample.asdDemo.model.alignpublic.MultipleValueAggregatedData;

import java.util.List;

public class MultipleValueAggregatedDataDao {
  public static final String LIST_OF_EMPLOYERS = "ListOfEmployers";
  public static final String LIST_OF_BACHELOR_DEGREES = "ListOfBachelorDegrees";
  public static final String LIST_OF_STUDENTS_STATES = "ListOfStudentsStates";
  public static final String LIST_OF_DEGREES = "ListOfDegrees";

  private SessionFactory factory;

  public MultipleValueAggregatedDataDao() {
    this.factory = PublicSessionFactory.getFactory();
  }

  public MultipleValueAggregatedDataDao(boolean test) {
    if (test) {
      this.factory = PublicTestSessionFactory.getFactory();
    }
  }

  /**
   * Update the specific list of the aggregated data in the public database.
   *
   * @param list list of the multiple value aggregated data.
   * @return true if updated.
   */
  public synchronized boolean saveOrUpdateList(List<MultipleValueAggregatedData> list) {
    Transaction tx = null;
    for (MultipleValueAggregatedData employer : list) {
      Session session = factory.openSession();
      try {
        tx = session.beginTransaction();
        session.saveOrUpdate(employer);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    }
    return true;
  }

  /**
   * Get the list of top five employers for the chatbot functionality.
   * This function is for machine learning api.
   *
   * @return list of top five employers.
   */
  public List<String> getTopFiveListOfEmployers() {
    return findTopFiveKeyByTerm(LIST_OF_EMPLOYERS);
  }

  /**
   * Get the list of top five bachelor degrees for the chatbot functionality.
   * This function is for machine learning api.
   *
   * @return list of top five Bachelor Degrees.
   */
  public List<String> getTopFiveListOfBachelorDegrees() {
    return findTopFiveKeyByTerm(LIST_OF_BACHELOR_DEGREES);
  }

  /**
   * Helper class for getting the top five values from the public database.
   *
   * @param analyticTerm term for searching specific values.
   * @return list of top five values in strings.
   */
  private List<String> findTopFiveKeyByTerm(String analyticTerm) {
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(
              "SELECT analyticKey " +
                      "FROM MultipleValueAggregatedData " +
                      "WHERE analyticTerm = :analyticTerm " +
                      "ORDER BY analyticValue DESC ");
      query.setParameter("analyticTerm", analyticTerm);
      query.setMaxResults(5);
      return (List<String>) query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Get the list of states that current students originally from
   * and their counts.
   *
   * @return list of states of current students originally from and their counts.
   */
  public List<DataCount> getListOfStudentsStatesCount() {
    return findDataCountByTerm(LIST_OF_STUDENTS_STATES);
  }

  /**
   * Get the list of highest degrees, such as Bachelor's or Master's,
   * that current students had and their counts.
   *
   * @return list of highest degrees of current students had
   * and their counts.
   */
  public List<DataCount> getListOfHighestDegreesCount() {
    return findDataCountByTerm(LIST_OF_DEGREES);
  }

  /**
   * Helper class to get the list of values and their data counts.
   *
   * @param analyticTerm specific term to get a specific list of data.
   * @return list of the data values and their counts.
   */
  private List<DataCount> findDataCountByTerm(String analyticTerm) {
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(
              "SELECT NEW org.mehaexample.asdDemo.model.alignpublic.DataCount( " +
                      "analyticKey, analyticValue ) " +
                      "FROM MultipleValueAggregatedData " +
                      "WHERE analyticTerm = :analyticTerm " +
                      "ORDER BY analyticValue DESC ");
      query.setParameter("analyticTerm", analyticTerm);
      return (List<DataCount>) query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Delete the list of Employers from the public Database.
   *
   * @return true if deleted.
   */
  public boolean deleteListOfEmployers() {
    return deleteDataByTerm(LIST_OF_EMPLOYERS);
  }

  /**
   * Delete the list of Bachelor Degrees from the public Database.
   *
   * @return true if deleted.
   */
  public boolean deleteListOfBachelorDegrees() {
    return deleteDataByTerm(LIST_OF_BACHELOR_DEGREES);
  }

  /**
   * Delete the list of Students States Counts from the public Database.
   *
   * @return true if deleted.
   */
  public boolean deleteListOfStudentsStatesCounts() {
    return deleteDataByTerm(LIST_OF_STUDENTS_STATES);
  }

  /**
   * Delete the list of Highest Degrees Counts from the public Database.
   *
   * @return true if deleted.
   */
  public boolean deleteListOfHighestDegreesCounts() {
    return deleteDataByTerm(LIST_OF_DEGREES);
  }

  /**
   * Helper class to find the original data info from the public database for
   * specific analaytic term.
   *
   * @param analyticTerm specific term to search the data,
   *                     such as LIST_OF_DEGREES.
   * @return the list of original multiple value aggregated data info
   * for specific data.
   */
  private List<MultipleValueAggregatedData> findDataByTerm(String analyticTerm) {
    Session session = factory.openSession();
    try {
      org.hibernate.query.Query query = session.createQuery(
              "FROM MultipleValueAggregatedData " +
                      "WHERE analyticTerm = :analyticTerm " +
                      "ORDER BY analyticValue DESC ");
      query.setParameter("analyticTerm", analyticTerm);
      return (List<MultipleValueAggregatedData>) query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Helper class to delete data based on the specific analytic term.
   *
   * @param analyticTerm specific term to search the data,
   *                     such as LIST_OF_DEGREES.
   * @return true if deleted.
   */
  private synchronized boolean deleteDataByTerm(String analyticTerm) {
    List<MultipleValueAggregatedData> listOfData = findDataByTerm(analyticTerm);
    for (MultipleValueAggregatedData data : listOfData) {
      Session session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(data);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    }

    return true;
  }
}
