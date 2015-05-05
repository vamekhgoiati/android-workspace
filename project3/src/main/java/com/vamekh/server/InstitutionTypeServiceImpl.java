package com.vamekh.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.sencha.gxt.data.shared.loader.PagingLoader;
import com.vamekh.client.InstitutionTypeService;
import com.vamekh.shared.Institution;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionType;
import com.vamekh.shared.InstitutionTypeDTO;

public class InstitutionTypeServiceImpl extends RemoteServiceServlet implements
		InstitutionTypeService {

	private static final long serialVersionUID = 2L;

	private EntityManager em;

	public InstitutionTypeDTO addInstitutionType(InstitutionTypeDTO typeDTO) {

		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		InstitutionType type = new InstitutionType(typeDTO);
		em.persist(type);
		em.getTransaction().commit();

		return typeDTO;

	}

	public boolean deleteInstitutionType(int id) {

		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(InstitutionType.class, id));
		em.getTransaction().commit();

		return true;

	}

	public boolean deleteInstitutionTypes(
			List<InstitutionTypeDTO> types) {

		for (InstitutionTypeDTO type : types) {
			if(!typeIsUsed(type)){
				deleteInstitutionType(type.getId());
			}
		}

		return true;
	}

	public ArrayList<InstitutionTypeDTO> getInstitutionTypes() {

		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<InstitutionType> types = new ArrayList<InstitutionType>(em
				.createQuery("SELECT e FROM InstitutionType e",
						InstitutionType.class).getResultList());
		ArrayList<InstitutionTypeDTO> typeDTOs = new ArrayList<InstitutionTypeDTO>(
				types != null ? types.size() : 0);
		if (types != null) {
			for (InstitutionType type : types) {
				typeDTOs.add(createInstitutionTypeDTO(type));
			}
		}
		em.getTransaction().commit();

		return typeDTOs;

	}

	public InstitutionTypeDTO getInstitutionType(int id) {

		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		InstitutionType type = em.find(InstitutionType.class, id);
		InstitutionTypeDTO typeDTO = createInstitutionTypeDTO(type);
		em.getTransaction().commit();

		return typeDTO;

	}

	public InstitutionTypeDTO updateInstitutionType(InstitutionTypeDTO typeDTO) {

		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		InstitutionType type = em.find(InstitutionType.class, typeDTO.getId());
		type.setCode(typeDTO.getCode());
		type.setName(typeDTO.getName());
		em.getTransaction().commit();

		return typeDTO;

	}

	public boolean isTypeUnique(String code, int id) {

		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<InstitutionType> types = new ArrayList<InstitutionType>(
				em.createQuery(
						"SELECT e FROM InstitutionType e WHERE e.code = :typeCode AND id != :typeId",
						InstitutionType.class).setParameter("typeCode", code)
						.setParameter("typeId", id).getResultList());
		em.getTransaction().commit();

		return types.size() == 0;

	}

	private InstitutionTypeDTO createInstitutionTypeDTO(InstitutionType instType) {
		return new InstitutionTypeDTO(instType.getId(), instType.getCode(),
				instType.getName());
	}

	public PagingLoadResult<InstitutionTypeDTO> getInstitutionTypes(
			PagingLoadConfig config) {

		ArrayList<InstitutionTypeDTO> typeDTOs = getInstitutionTypes();
		ArrayList<InstitutionTypeDTO> sublist = new ArrayList<InstitutionTypeDTO>();

		if (config.getSortInfo().size() > 0) {
			SortInfo sort = config.getSortInfo().get(0);
			if (sort.getSortField() != null) {
				final String sortField = sort.getSortField();
				if (sortField != null) {
					Collections.sort(typeDTOs, sort.getSortDir().comparator(new Comparator<InstitutionTypeDTO>() {
						public int compare(InstitutionTypeDTO t1, InstitutionTypeDTO t2) {
							if (sortField.equals("code")) {
								return t1.getCode().compareTo(t2.getCode());	
							} else if (sortField.equals("name")) {
								return t1.getName().compareTo(t2.getName());
							}
							
							return 0;
							
						}
					}));
				}
			}
		}

		int start = config.getOffset();
		int limit = typeDTOs.size();

		if (config.getLimit() > 0) {
			limit = Math.min(start + config.getLimit(), limit);
		}

		for (int i = config.getOffset(); i < limit; i++) {
			sublist.add(typeDTOs.get(i));
		}

		return new PagingLoadResultBean<InstitutionTypeDTO>(sublist,
				typeDTOs.size(), config.getOffset());

	}

	
	
	public boolean typeIsUsed(InstitutionTypeDTO type) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		InstitutionType instType = new InstitutionType(type);
		ArrayList<Institution> institutions = new ArrayList<Institution>(em.createQuery("SELECT e FROM Institution e WHERE e.type = :type", Institution.class).setParameter("type", instType).getResultList());
		em.getTransaction().commit();
		
		return institutions.size() > 0;
	}

	public ArrayList<InstitutionTypeDTO> checkTypes(
			List<InstitutionTypeDTO> typeDTOs) {
		
		ArrayList<InstitutionTypeDTO> usedTypes = new ArrayList<InstitutionTypeDTO>();
		
		for(InstitutionTypeDTO typeDTO : typeDTOs){
			if(typeIsUsed(typeDTO)){
				usedTypes.add(typeDTO);
			}
		}
		return usedTypes;
	}
}
