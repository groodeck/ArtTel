package org.arttel.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.arttel.entity.SellerBankAccount;
import org.springframework.stereotype.Repository;

@Repository
@org.springframework.transaction.annotation.Transactional
public class SellerBankAccountDao extends BaseDao {

	@PersistenceContext
	private EntityManager em;
	
	public SellerBankAccount getBankAccountById(String bankAccountId) {
		return em.find(SellerBankAccount.class, new Integer(bankAccountId));
	}

	public void deleteOrderById(final String bankAccountId) {
		if (bankAccountId != null && !"".equals(bankAccountId)) {
			SellerBankAccount bankAccount = em.find(SellerBankAccount.class, new Integer(bankAccountId));
			em.remove(bankAccount);
		}
	}
}
