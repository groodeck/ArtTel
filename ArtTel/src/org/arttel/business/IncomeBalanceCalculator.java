package org.arttel.business;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.DealingVO;
import org.arttel.controller.vo.FundsEntryVO;
import org.arttel.controller.vo.UserBalanceVO;
import org.arttel.controller.vo.filter.DealingFilterVO;
import org.arttel.dao.DealingDAO;
import org.arttel.dao.FundsEntryDAO;
import org.arttel.dao.UserDAO;
import org.arttel.dictionary.DealingType;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IncomeBalanceCalculator {

private final Logger log = Logger.getLogger(SqueezeBalanceCalculator.class);

	@Autowired
	private UserDAO userDao;

	@Autowired
	private DealingDAO dealingDao;

	@Autowired
	private FundsEntryDAO fundsEntryDao;
	
	public List<UserBalanceVO> getUsersBalance(){
		
		List<UserBalanceVO> userBalanceList = new ArrayList<UserBalanceVO>();
		try {
			List<String> userList = getUserList();
			for(String userName : userList){
				UserBalanceVO userBalance = getUserBalance(userName);
				userBalanceList.add(userBalance);
			}
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		return userBalanceList;
	}

	public UserBalanceVO getUserBalance(String userName) throws DaoException {

		final List<FundsEntryVO> fundsEntryList = fundsEntryDao.getUserFundsEntryList(userName);
		final List<DealingVO> userDealingList = dealingDao.getUserDealingList(userName);

		final BigDecimal costsSum = calculateDealingSum(userDealingList, DealingType.COSTS);
		final BigDecimal incomeSum = calculateDealingSum(userDealingList, DealingType.INCOME);
		final BigDecimal gain = incomeSum.subtract(costsSum);
		final BigDecimal fundsSum = calculateFundsEntrySum(fundsEntryList);
		final BigDecimal amountLeft = fundsSum.subtract(costsSum);
		
		final UserBalanceVO userBalance = new UserBalanceVO();
		userBalance.setUserName(userName);
		userBalance.setIncomeSum(incomeSum.toPlainString());
		userBalance.setCostSum(costsSum.toPlainString());
		userBalance.setAmountLeft(amountLeft.toPlainString());
		userBalance.setGain(gain.toPlainString());
		return userBalance;
	}

	private BigDecimal calculateDealingSum(List<DealingVO> userDealingList, final DealingType dealingType) {

		BigDecimal sum = new BigDecimal(0);
		for(DealingVO dealing : userDealingList){
			if(dealing.getDealingType() == dealingType){
				BigDecimal amount = getAmount(dealing);
				sum = sum.add(amount);
			}
		}
		return sum;
	}

	private BigDecimal getAmount(final DealingVO dealing) {
		final BigDecimal amount;
		if(StringUtils.isNotEmpty(dealing.getAmount())){
			amount = new BigDecimal(dealing.getAmount());
		} else {
			amount = BigDecimal.ZERO;
		}
		return amount;
	}
	
	private List<String> getUserList() {
		List<String> userList = new ArrayList<String>();
		try {
			userList = userDao.getUserList();
		} catch (DaoException e) {
			log.error("DaoException", e);
		}
		return userList;
	}

	public BigDecimal calculateFundsEntrySum(List<FundsEntryVO> fundsEntryList) {

		BigDecimal sum = new BigDecimal(0);
		for(FundsEntryVO fundsEntry : fundsEntryList){
			final String entryAmount = fundsEntry.getEntryAmount();
			final BigDecimal entryValue = new BigDecimal(entryAmount);
			sum = sum.add(entryValue);
		}
		return sum;
	}

	public UserBalanceVO getCompanyBalance(final String dealingYear) {

		final UserBalanceVO userBalance = new UserBalanceVO();
		try {
			final DealingFilterVO dealingYearFilter = getDealingYearFilter(dealingYear);
			
			final List<DealingVO> dealingList = dealingDao.getDealingList(dealingYearFilter);
			final BigDecimal costsSum = calculateDealingSum(dealingList, DealingType.COSTS);
			final BigDecimal incomeSum = calculateDealingSum(dealingList, DealingType.INCOME);
			final BigDecimal gain = incomeSum.subtract(costsSum);
			
			userBalance.setDealingYear(dealingYear);
			userBalance.setIncomeSum(incomeSum.toPlainString());
			userBalance.setCostSum(costsSum.toPlainString());
			userBalance.setGain(gain.toPlainString());
			
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return userBalance;
	}

	private DealingFilterVO getDealingYearFilter(String dealingYear) {
		final DealingFilterVO dealingFilterVO = new DealingFilterVO();
		final Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Integer.parseInt(dealingYear), 0, 1);
		dealingFilterVO.setDateFrom(new Date(calendar.getTime().getTime()));
		calendar.set(Integer.parseInt(dealingYear), 11, 31);
		dealingFilterVO.setDateTo(new Date(calendar.getTime().getTime()));
		return dealingFilterVO;
	}
}
