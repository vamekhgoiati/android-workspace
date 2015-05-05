package com.vamekh.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.vamekh.client.ReturnService;
import com.vamekh.shared.Institution;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionType;
import com.vamekh.shared.InstitutionTypeDTO;
import com.vamekh.shared.Return;
import com.vamekh.shared.ReturnDTO;
import com.vamekh.shared.Template;
import com.vamekh.shared.TemplateDTO;

public class ReturnServiceImpl extends RemoteServiceServlet implements ReturnService{

	private static final long serialVersionUID = 4L;

	EntityManager em;
	
	public ReturnDTO addReturn(ReturnDTO retDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Return ret = new Return(retDTO);
		em.persist(ret);
		em.getTransaction().commit();
		
		return retDTO;
		
	}

	public boolean deleteReturn(int id) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Return.class, id));
		em.getTransaction().commit();
		
		return true;
	
	}

	public boolean deleteReturns(List<ReturnDTO> returns) {
		
		for(ReturnDTO ret : returns){
			deleteReturn(ret.getId());
		}
		
		return true;
	}

	public ArrayList<ReturnDTO> getReturns() {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<Return> returns = new ArrayList<Return>(em.createQuery("SELECT e FROM Return e", Return.class).getResultList());
		ArrayList<ReturnDTO> returnDTOs = new ArrayList<ReturnDTO>( returns != null ? returns.size() : 0);
		if(returns != null){
			for(Return ret : returns){
				returnDTOs.add(createReturnDTO(ret));
			}
		}
		em.getTransaction().commit();
		
		return returnDTOs;
	
	}

	public ReturnDTO getReturn(int id) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Return ret = em.find(Return.class, id);
		ReturnDTO retDTO = createReturnDTO(ret);
		em.getTransaction().commit();
		
		return retDTO;
		
	}

	public ReturnDTO updateReturn(ReturnDTO retDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Return ret = em.find(Return.class, retDTO.getId());
		ret.setCode(retDTO.getCode());
		ret.setDescription(retDTO.getDescription());
		ret.setInstitution(new Institution(retDTO.getInstitution()));
		ret.setTemplate(new Template(retDTO.getTemplate()));
		em.getTransaction().commit();
		
		return retDTO;
		
	}

	public boolean isReturnCodeUnique(String code, int id) {

		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<Return> returns = new ArrayList<Return>(em.createQuery("SELECT e FROM Return e WHERE e.code = :retCode AND id != :retId", Return.class).setParameter("retCode", code).setParameter("retId", id).getResultList());
		em.getTransaction().commit();
		
		return returns.size() == 0;
		
	}
	
	private ReturnDTO createReturnDTO(Return ret){
		InstitutionDTO instDTO = createInstitutionDTO(ret.getInstitution());
		TemplateDTO tempDTO = createTemplateDTO(ret.getTemplate());
		return new ReturnDTO(ret.getId(), ret.getCode(), ret.getDescription(), instDTO, tempDTO);
	}
	
	private InstitutionDTO createInstitutionDTO(Institution inst){
		InstitutionTypeDTO typeDTO = createInstitutionTypeDTO(inst.getType());
		return new InstitutionDTO(inst.getId(), inst.getCode(), inst.getName(), inst.getAddress(), inst.getPhone(), inst.getEmail(), inst.getFax(), typeDTO, inst.getRegDate());
	}
	
	private InstitutionTypeDTO createInstitutionTypeDTO(InstitutionType instType){
		return new InstitutionTypeDTO(instType.getId(), instType.getCode(), instType.getName());
	}
	
	private TemplateDTO createTemplateDTO(Template template){
		return new TemplateDTO(template.getId(), template.getCode(), template.getDescription(), template.getSchedule());
	}

	public PagingLoadResult<ReturnDTO> getReturns(PagingLoadConfig config) {
		
		ArrayList<ReturnDTO> returnDTOs = getReturns();
		ArrayList<ReturnDTO> sublist = new ArrayList<ReturnDTO>();

		if (config.getSortInfo().size() > 0) {
			SortInfo sort = config.getSortInfo().get(0);
			if (sort.getSortField() != null) {
				final String sortField = sort.getSortField();
				if (sortField != null) {
					Collections.sort(returnDTOs, sort.getSortDir().comparator(new Comparator<ReturnDTO>() {
						public int compare(ReturnDTO r1, ReturnDTO r2) {
							if (sortField.equals("code")) {
								return r1.getCode().compareTo(r2.getCode());	
							} else if (sortField.equals("description")) {
								return r1.getDescription().compareTo(r2.getDescription());
							}
							
							return 0;
							
						}
					}));
				}
			}
		}

		int start = config.getOffset();
		int limit = returnDTOs.size();

		if (config.getLimit() > 0) {
			limit = Math.min(start + config.getLimit(), limit);
		}

		for (int i = config.getOffset(); i < limit; i++) {
			sublist.add(returnDTOs.get(i));
		}

		return new PagingLoadResultBean<ReturnDTO>(sublist,
				returnDTOs.size(), config.getOffset());

	}
	
	

}
