package org.arttel.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.arttel.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(classes={AppConfig.class})//TODO: add test AppConfig and add persistence-context
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class InstallationDAOTest {

	@Autowired
	private InstalationDAO dao;
	@PersistenceContext
	private EntityManager em;


	@Test
	public void test() throws Exception {

		final long before = System.currentTimeMillis();
		em.createQuery(
				"select i from Installation as i"
						+ " left join fetch i.devices as dvcs"
						+ " where i.instalationId is not null"
						+ " and i.city='skierniewice' ")
						.getResultList();

		System.out.println("Execution time: " + (System.currentTimeMillis() - before));
	}

}
