package org.arttel.business;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.arttel.controller.vo.SqueezeBalanceVO;
import org.arttel.controller.vo.SqueezeVO;
import org.arttel.dao.SqueezeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqueezeBalanceCalculator {

private final Logger log = Logger.getLogger(IncomeBalanceCalculator.class);
	
	@Autowired
	private SqueezeDAO squeezeDao;
	
	public SqueezeBalanceVO getSqueezeBalance(){
		
		List<SqueezeVO> squeezeList = getSqueezeList();
		return calculateSqueezeBalance(squeezeList);
	}

	private SqueezeBalanceVO calculateSqueezeBalance(List<SqueezeVO> squeezeList) {

		int squeezeCount = 0;
		int squeezeMeters = 0;
		Double amountSum = new Double(0);
		for(final SqueezeVO squeeze : squeezeList){
			squeezeCount += squeeze.getQuantity();
			squeezeMeters += squeeze.getMeters();
			amountSum = add(amountSum, squeeze.getAmount());
		}
		return new SqueezeBalanceVO(squeezeCount, squeezeMeters, amountSum.toString());
	}

	private Double add(Double sum, String amount) {
		final Double result;
		if(StringUtils.isNotEmpty(amount)){
			final Double additive = getDouble(amount);
			result = sum + additive;
		} else {
			result = sum;
		}
		return result;
	}

	private Double getDouble(String amount) {
		Double value;
		try{
			value = Double.valueOf(amount);
		} catch (NumberFormatException e) {
			value = new Double(0);
		}
		return value;
	}

	private List<SqueezeVO> getSqueezeList() {
		return squeezeDao.getSqueezeList();
	}
}