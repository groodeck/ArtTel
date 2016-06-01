package org.arttel.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.InstallationVO;
import org.arttel.controller.vo.filter.InstallationFilterVO;
import org.arttel.converter.InstallationConverter;
import org.arttel.dao.InstalationDAO;
import org.arttel.entity.Installation;
import org.arttel.ui.PageInfo;
import org.arttel.ui.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class InstalationService {

	private final static Logger log = Logger.getLogger(InstalationService.class);

	@Autowired
	private InstalationDAO instalationDao;

	@Autowired
	private InstallationConverter installationConverter;

	public void clearInstalations(final String city) {
		instalationDao.clearInstalations(city);
	}

	public void closeInstalations(final List<Integer> instalationIds, final String user) {
		for(final Integer instalationId : instalationIds){
			final InstallationVO installation = getInstallation(instalationId, user);
			if(installation.isEditable() && installation.isClosable()){
				instalationDao.closeInstalation(instalationId);
			}
		}
	}

	public void deleteInstalations(final List<Integer> instalationIds, final String user) {
		for(final Integer instalationId : instalationIds){
			final InstallationVO installation = getInstallation(instalationId, user);
			if(installation.isEditable()){
				instalationDao.deleteInstalationById(instalationId);
			}
		}
	}

	public InstallationVO getInstallation(final Integer instalationId, final String user) {
		final Installation entity = instalationDao.getInstallationById(instalationId);
		final InstallationVO convert = installationConverter.convert(entity);
		convert.applyPermissions(user);
		return convert;
	}

	public ResultPage<InstallationVO> getInstallationList(final InstallationFilterVO installationFilterVO) {
		final ResultPage<Installation> entityList = instalationDao.getInstalationList(installationFilterVO);
		return installationConverter.convert(entityList);
	}

	public ResultPage<InstallationVO> getInstallationList(final InstallationFilterVO installationFilterVO, final PageInfo pageInfo) {
		final ResultPage<Installation> entityList = instalationDao.getInstalationList(installationFilterVO, pageInfo);
		return installationConverter.convert(entityList);
	}

	public ResultPage<InstallationVO> getInstallationList(final InstallationFilterVO installationFilterVO, final PageInfo pageInfo,
			final String user) {

		final ResultPage<Installation> entityList = instalationDao.getInstalationList(installationFilterVO, pageInfo);

		final ResultPage<InstallationVO> resultList = installationConverter.convert(entityList);
		for(final InstallationVO installationVO : resultList.getRecords()){
			installationVO.applyPermissions(user);
		}
		return resultList;
	}

	public int getSocketCount(final String city) {
		return instalationDao.getSocketCount(city);
	}

	public int getUnclearedInstalationCount(final String city) {
		return instalationDao.getUnclearedInstalationCount(city);
	}

	public void resetSocketOrder(final String city) {
		instalationDao.resetSocketOrder(city);
	}

	public void save(final InstallationVO installationVO, final String userName) {
		final Installation entity = installationConverter.convert(installationVO, userName);
		final Integer installationId = installationVO.getInstallationId();
		if(installationId != null){
			final Installation current = instalationDao.getInstallationById(installationVO.getInstallationId());
			entity.setSocketOrderSequence(current.getSocketOrderSequence());
			entity.setInstalationClearingSequence(current.getInstalationClearingSequence());
		}
		instalationDao.save(entity, userName);
	}
}
