package org.arttel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.arttel.controller.vo.OrderVO;
import org.arttel.controller.vo.SimpleComboElement;
import org.arttel.controller.vo.filter.OrderFilterVO;
import org.arttel.dictionary.OrderType;
import org.arttel.dictionary.Status;
import org.arttel.entity.Order;
import org.arttel.exception.DaoException;
import org.arttel.generator.CellType;
import org.arttel.generator.DataCell;
import org.arttel.generator.DataSheet;
import org.arttel.generator.report.ReportDataVO;
import org.arttel.util.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Repository
@org.springframework.transaction.annotation.Transactional
public class OrderDAO extends BaseDao {

	@PersistenceContext
	private EntityManager em;
	
	private final Logger log = Logger.getLogger(OrderDAO.class);
	
	private Function<Order, OrderVO> orderMapper = new Function<Order, OrderVO>() {

		@Override
		public OrderVO apply(Order entityOrder) {
			final OrderVO singleOrder = new OrderVO();
			singleOrder.setOrderId(entityOrder.getOrderId().toString());
			final String statusIdn = entityOrder.getStatus();
			if(statusIdn != null){
				singleOrder.setStatus(Status.getValueByIdn(statusIdn));
			}
			final String orderTypeIdn = entityOrder.getOrderType();
			if(orderTypeIdn != null){
				singleOrder.setOrderType(OrderType.getValueByIdn(orderTypeIdn));
			}
			singleOrder.setIssueDate(entityOrder.getIssueDate());
			singleOrder.setName(entityOrder.getName());
			singleOrder.setSurname(entityOrder.getSurname());
			singleOrder.setAddress(entityOrder.getAddress());
			singleOrder.setCity(entityOrder.getCity());
			singleOrder.setBundle(entityOrder.getBundle());
			singleOrder.setSerialNumber(entityOrder.getSerialNumber());
			singleOrder.setRealizationDate(entityOrder.getRealizationDate());
			singleOrder.setSolution(entityOrder.getSolution());
			singleOrder.setComments(entityOrder.getComments());
			singleOrder.setAdditionalComments(entityOrder.getAdditionalComments());
			singleOrder.setUser(entityOrder.getUserId());
			singleOrder.setPhone(entityOrder.getPhone());
			singleOrder.setProblemDescription(entityOrder.getProblemDescription());
			
			return singleOrder;
		}
	};
	
	public OrderVO getOrderById(String orderId) {
		final Order orderEntity = em.find(Order.class, new Integer(orderId));
		return orderMapper.apply(orderEntity);
	}

	public void deleteOrderById(final String orderId) {
		if (orderId != null && !"".equals(orderId)) {
			Order order = em.find(Order.class, new Integer(orderId));
			em.remove(order);
		}
	}

//	@Autowired
//	private DataSource dataSource;
	
	public List<OrderVO> getOrderList(OrderFilterVO orderFilterVO) {
		final List<OrderVO> resultList = new ArrayList<OrderVO>();
		if(orderFilterVO == null){
			return resultList;
		}
		final CriteriaQuery<Order> query = prepareQueryCriteria(orderFilterVO);
		final List<Order> entityResults = em.createQuery(query).getResultList();
		return Lists.newArrayList(Collections2.transform(entityResults, orderMapper));
	}

	private CriteriaQuery<Order> prepareQueryCriteria(final OrderFilterVO orderFilterVO) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
		Root<Order> orderRoot = criteria.from(Order.class);
		
		if(orderFilterVO.getPhrase() != null){
			final String phrase = "%"+ orderFilterVO.getPhrase()+"%";
			criteria.where(builder.or(
					builder.like(orderRoot.<String> get("city"), phrase),
					builder.like(orderRoot.<String> get("address"), phrase),
					builder.like(orderRoot.<String> get("name"), phrase),
					builder.like(orderRoot.<String> get("surname"), phrase)));
		} else {
			if(orderFilterVO.getCity() != null){
				criteria.where(builder.and(builder.equal(orderRoot.get("city"), orderFilterVO.getCity())));
			}
			if(orderFilterVO.getStatus() != null){
				criteria.where(builder.and(builder.equal(orderRoot.get("status"), orderFilterVO.getStatus())));
			}
			if(orderFilterVO.getDateFrom() != null){
				criteria.where(builder.and(builder.greaterThanOrEqualTo(
						orderRoot.<Date>get("realizationDate"), orderFilterVO.getDateFrom())));
			}
			if(orderFilterVO.getDateTo() != null){
				criteria.where(builder.and(builder.lessThanOrEqualTo(
						orderRoot.<Date>get("realizationDate"), orderFilterVO.getDateTo())));
			}
		}
		criteria.orderBy(
				builder.desc(orderRoot.<String> get("realizationDate")), 
				builder.desc(orderRoot.<String> get("orderId")));
		return criteria;
	}


	@org.springframework.transaction.annotation.Transactional
	public void save(OrderVO orderVO, String userName) throws DaoException {
		final Order entity;
		if(orderVO.getOrderId() != null && !"".equals(orderVO.getOrderId())){
			entity = em.find(Order.class, new Integer(orderVO.getOrderId()));
		} else {
			entity = new Order();
		}
		updateWithData(entity, orderVO);
		em.persist(entity);
	}

	public void updateWithData(final Order entity, final OrderVO vo) {
		entity.setStatus(vo.getStatus().getIdn());
		entity.setOrderType(vo.getOrderType().getIdn());
		entity.setIssueDate(vo.getIssueDate());
		entity.setName(vo.getName());
		entity.setSurname(vo.getSurname());
		entity.setAddress(vo.getAddress());
		entity.setCity(vo.getCity());
		entity.setBundle(vo.getBundle());
		entity.setSerialNumber(vo.getSerialNumber());
		entity.setRealizationDate(vo.getRealizationDate());
		entity.setSolution(vo.getSolution());
		entity.setComments(vo.getComments());
		entity.setAdditionalComments(vo.getAdditionalComments());
		entity.setUserId(vo.getUser());
		entity.setPhone(vo.getPhone());
		entity.setProblemDescription(vo.getProblemDescription());
	}
	
	public ReportDataVO getReportData(String worksheetName, Date dateFrom, Date dateTo, String cityIdn) throws DaoException {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			final ReportDataVO result = new ReportDataVO();
			String dataQuery = "select o.orderId,o.status,o.orderType,o.issueDate,o.name,o.surname,o.address, "
				+ " c.cityDesc,o.bundle,o.serialNumber,o.realizationDate,o.solution,o.comments,o.additionalComments,o.userId, "
				+ " o.phone, o.problemDescription, o.comments "
				+ " from Orders o "
				+ " left join city c on o.city=c.cityIdn "
				+ " where 1 ";
			if(dateFrom != null){
				dataQuery = dataQuery.concat(" AND o.realizationDate >='" + dateFrom + "'");
			}
			if(dateTo != null){
				dataQuery = dataQuery.concat(" AND o.realizationDate <='" + dateTo + "'");
			}
			if(cityIdn != null){
				dataQuery = dataQuery.concat(" AND o.city ='" + cityIdn + "'");
			}
			stmt = getConnection().createStatement();
			rs = stmt.executeQuery(dataQuery);
			
			//TODO: dodaæ dynamiczny zapis metadanych instalacji, komorek niestandardowych do ReportDataVO.reportDetailsList
			
			int xlsRowNumber = 1, sheetRowsDelimiter = 39;
			DataSheet dataSheet = new DataSheet();
			dataSheet.setDataRowsOffset(3);
			while(rs.next()){
				
				final List<DataCell> row = extractDataRow(worksheetName, rs, xlsRowNumber++);
				dataSheet.getRows().add(row);
				
				if(sheetRowsDelimiter-- == 0){
					sheetRowsDelimiter = 39;
//					dataSheet.addDetailsCell(1,2, getCityInfo(cityIdn));
//					dataSheet.addDetailsCell(1,3, getDateInfo(dateFrom, dateTo));
					result.addDataSheet(dataSheet);
					dataSheet = new DataSheet();
					dataSheet.setDataRowsOffset(3);
				}
			}
			result.addDataSheet(dataSheet);
			
			return result;
			
		} catch (SQLException e) {
			throw new DaoException("OrderDAO SQLException", e);
		} finally {
			disconnect(stmt, rs);
		}
	}
	
	private List<DataCell> extractDataRow(final String worksheetName, final ResultSet rs, int xlsRowNumber) throws SQLException {
		
		//orderId,status,orderType,issueDate,name,surname,address,city,bundle,serialNumber,realizationDate,solution,comments,additionalComments,userId, phone, problemDescription
		
		final List<DataCell> row = new ArrayList<DataCell>();
		row.add(new DataCell(xlsRowNumber, CellType.INT));
		final Status status = Status.getValueByIdn(rs.getString(2));
		if(status != null){
			row.add(new DataCell(status.getDesc(),CellType.TEXT)); //status
		}
		final OrderType orderType = OrderType.getValueByIdn(rs.getString(3));
		if(orderType != null) {
			row.add(new DataCell(orderType.getDesc(),CellType.TEXT)); //orderType
		}
		row.add(new DataCell(rs.getDate(4),CellType.DATE)); // data wystawienie zlecenia
		row.add(new DataCell(rs.getString(16),CellType.TEXT)); // phone
		row.add(new DataCell(Translator.join(rs.getString(6),rs.getString(5)),CellType.TEXT)); // nazwisko imie
		row.add(new DataCell(rs.getString(7),CellType.TEXT));
		row.add(new DataCell(rs.getString(8),CellType.TEXT)); //miejscowosc
		row.add(new DataCell(rs.getString(9),CellType.TEXT)); //pakiet
		row.add(new DataCell(rs.getDate(11),CellType.DATE)); //data realizacji
		row.add(new DataCell(rs.getString(15),CellType.TEXT)); //monter
		row.add(new DataCell(rs.getString(17),CellType.TEXT)); //problemDescription
		row.add(new DataCell(rs.getString(18),CellType.TEXT)); //comments
		
		return row;
	}
	
	public void closeOrder(String orderId) {
		final Order entity = em.find(Order.class, new Integer(orderId));
		entity.setStatus(Status.DONE.getIdn());
	}
}
