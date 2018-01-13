package org.arttel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.SqueezeVO;
import org.arttel.controller.vo.filter.SqueezeFilterVO;
import org.arttel.converter.SqueezeConverter;
import org.arttel.dao.SqueezeDAO;
import org.arttel.entity.Squeeze;
import org.arttel.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class SqueezeService {
	private final static Logger log = Logger.getLogger(SqueezeService.class);

	@Autowired
	private SqueezeDAO squeezeDao;

	@Autowired
	private SqueezeConverter squeezeConverter;

	public void deleteSqueezeById(final String squeezeId) {
		try {
			squeezeDao.deleteSqueezeById(squeezeId);
		} catch (final DaoException e) {
			log.error("SqueezeDAO.deleteById() error", e);
		}
	}

	public SqueezeVO getSqueezeById(final String squeezeId) {
		try {
			return squeezeDao.getSqueezeById(squeezeId);
			//			return squeezeConverter.convert(entity);
		} catch (final DaoException e) {
			log.error("SqueezeDAO.getSqueezeById() error", e);
			return null;
		}
	}

	public List<SqueezeVO> getSqueezeList(final SqueezeFilterVO squeezeFilterVO) {
		try {
			return squeezeDao.getSqueezeList(squeezeFilterVO);
			//			return squeezeConverter.convert(entityList);
		} catch (final DaoException e) {
			log.error("SqueezeDAO:getSqueezeList() error", e);
			return null;
		}
	}

	public void save(final SqueezeVO squeezeVO, final String userName) {
		try {
			final Squeeze entity = squeezeConverter.convert(squeezeVO, userName);
			//TODO: change dao to take entity and pass converted entity above to dao
			squeezeDao.save(squeezeVO, userName);
		} catch (final DaoException e) {
			log.error("Squeeze DAO creation exception", e);
		}
	}
}
