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
import com.vamekh.client.InstitutionService;
import com.vamekh.shared.Institution;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionType;
import com.vamekh.shared.InstitutionTypeDTO;
import com.vamekh.shared.Return;
import com.vamekh.shared.Template;
import com.vamekh.shared.TemplateDTO;

public class InstitutionServiceImpl extends RemoteServiceServlet implements InstitutionService{

	private static final long serialVersionUID = 1L;

	EntityManager em;
	
	public InstitutionDTO addInstitution(InstitutionDTO instDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Institution inst = new Institution(instDTO);
		em.persist(inst);
		em.getTransaction().commit();
		
		return instDTO;
		
	}

	public boolean deleteInstitution(int id) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Institution.class, id));
		em.getTransaction().commit();
		
		return true;
		
	}

	public boolean deleteInstitutions(List<InstitutionDTO> institutions) {
		
		for(InstitutionDTO inst : institutions){
			if(!institutionIsUsed(inst)){
				deleteInstitution(inst.getId());
			}
		}
		
		return true;
	}

	public ArrayList<InstitutionDTO> getInstitutions() {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<Institution> institutions = new ArrayList<Institution>(em.createQuery("SELECT e FROM Institution e", Institution.class).getResultList());
		ArrayList<InstitutionDTO> institutionDTOs = new ArrayList<InstitutionDTO>( institutions != null ? institutions.size() : 0);
		if(institutions != null){
			for(Institution inst : institutions){
				institutionDTOs.add(createInstitutionDTO(inst));
			}
		}
		em.getTransaction().commit();
		
		return institutionDTOs;
		
	}

	public InstitutionDTO getInstitution(int id) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Institution inst = em.find(Institution.class, id);
		InstitutionDTO instDTO = createInstitutionDTO(inst);
		em.getTransaction().commit();
		
		return instDTO;
		
	}

	public InstitutionDTO updateInstitution(InstitutionDTO instDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Institution inst = em.find(Institution.class, instDTO.getId());
		inst.setCode(instDTO.getCode());
		inst.setName(instDTO.getName());
		inst.setAddress(instDTO.getAddress());
		inst.setPhone(instDTO.getPhone());
		inst.setEmail(instDTO.getEmail());
		inst.setType(new InstitutionType(instDTO.getType()));
		inst.setFax(instDTO.getFax());
		inst.setRegDate(instDTO.getRegDate());
		em.getTransaction().commit();
		
		return instDTO;
	
	}

	public boolean institutionCodeIsUnique(String code, int id) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<Institution> institutions = new ArrayList<Institution>(em.createQuery("SELECT e FROM Institution e WHERE e.code = :instCode AND id != :instId", Institution.class).setParameter("instCode", code).setParameter("instId", id).getResultList());
		em.getTransaction().commit();
		
		return institutions.size() == 0;
		
	}
	
	public PagingLoadResult<InstitutionDTO> getInstitutions(
			PagingLoadConfig config) {

		ArrayList<InstitutionDTO> instDTOs = getInstitutions();
		ArrayList<InstitutionDTO> sublist = new ArrayList<InstitutionDTO>();

		if (config.getSortInfo().size() > 0) {
			SortInfo sort = config.getSortInfo().get(0);
			if (sort.getSortField() != null) {
				final String sortField = sort.getSortField();
				if (sortField != null) {
					Collections.sort(instDTOs, sort.getSortDir().comparator(new Comparator<InstitutionDTO>() {
						public int compare(InstitutionDTO inst1, InstitutionDTO inst2) {
							if (sortField.equals("code")) {
								return inst1.getCode().compareTo(inst2.getCode());	
							} else if (sortField.equals("name")) {
								return inst1.getName().compareTo(inst2.getName());
							}
							
							return 0;
							
						}
					}));
				}
			}
		}

		int start = config.getOffset();
		int limit = instDTOs.size();

		if (config.getLimit() > 0) {
			limit = Math.min(start + config.getLimit(), limit);
		}

		for (int i = config.getOffset(); i < limit; i++) {
			sublist.add(instDTOs.get(i));
		}

		return new PagingLoadResultBean<InstitutionDTO>(sublist,
				instDTOs.size(), config.getOffset());

	}
	
	private InstitutionTypeDTO createInstitutionTypeDTO(InstitutionType instType){
		return new InstitutionTypeDTO(instType.getId(), instType.getCode(), instType.getName());
	}
	
	private InstitutionDTO createInstitutionDTO(Institution inst){
		InstitutionTypeDTO typeDTO = createInstitutionTypeDTO(inst.getType());
		return new InstitutionDTO(inst.getId(), inst.getCode(), inst.getName(), inst.getAddress(), inst.getPhone(), inst.getEmail(), inst.getFax(), typeDTO, inst.getRegDate());
	}

	public boolean institutionIsUsed(InstitutionDTO instDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Institution inst = new Institution(instDTO);
		ArrayList<Return> retunrs = new ArrayList<Return>(em.createQuery("SELECT e FROM Return e where e.institution = :institution", Return.class).setParameter("institution", inst).getResultList());
		
		return retunrs.size() > 0;
	}

	public ArrayList<InstitutionDTO> checkInstitutions(
			List<InstitutionDTO> instDTOs) {
		
		ArrayList<InstitutionDTO> usedInstitutions = new ArrayList<InstitutionDTO>();
		
		for(InstitutionDTO instDTO : instDTOs){
			if(institutionIsUsed(instDTO)){
				usedInstitutions.add(instDTO);
			}
		}
		return usedInstitutions;
	}


}
