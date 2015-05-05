package com.vamekh.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.shared.SortInfo;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import com.vamekh.client.TemplateService;
import com.vamekh.shared.Institution;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.Return;
import com.vamekh.shared.Template;
import com.vamekh.shared.TemplateDTO;

public class TemplateServiceImpl extends RemoteServiceServlet implements TemplateService{

	private static final long serialVersionUID = 3L;

	private EntityManager em;
	
	public TemplateDTO addTemplate(TemplateDTO templateDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Template template = new Template(templateDTO);
		em.persist(template);
		em.getTransaction().commit();
		
		return templateDTO;
		
	}

	public boolean deleteTemplate(int id) {
	
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Template.class, id));
		em.getTransaction().commit();
		
		return true;
		
	}

	public boolean deleteTemplates(List<TemplateDTO> templates) {
		
		for(TemplateDTO tmp : templates){
			if(!templateIsUsed(tmp)){
				deleteTemplate(tmp.getId());
			}
		}
		
		return true;
		
	}

	public ArrayList<TemplateDTO> getTemplates() {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<Template> templates = new ArrayList<Template>(em.createQuery("SELECT e FROM Template e", Template.class).getResultList());
		ArrayList<TemplateDTO> templateDTOs = new ArrayList<TemplateDTO>( templates != null ? templates.size() : 0);
		if(templates != null){
			for(Template template : templates){
				templateDTOs.add(createTemplateDTO(template));
			}
		}
		em.getTransaction().commit();
		
		return templateDTOs;
		
	}

	public TemplateDTO getTemplate(int id) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Template template = (Template) em.find(Template.class, id);
		TemplateDTO templateDTO = createTemplateDTO(template);
		em.getTransaction().commit();
		
		return templateDTO;
		
	}

	public TemplateDTO updateTemplate(TemplateDTO templateDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Template template = em.find(Template.class, templateDTO.getId());
		template.setCode(templateDTO.getCode());
		template.setDescription(templateDTO.getDescription());
		template.setSchedule(templateDTO.getSchedule());
		em.getTransaction().commit();
		
		return templateDTO;
		
	}
	
	public boolean codeIsUnique(String code, int id){
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		ArrayList<Template> templates = new ArrayList<Template>(em.createQuery("SELECT e FROM Template e WHERE e.code = :tmpCode AND id != :tmpId", Template.class).setParameter("tmpCode", code).setParameter("tmpId", id).getResultList());
		em.getTransaction().commit();
		
		return templates.size() == 0;
		
	}
	
	public PagingLoadResult<TemplateDTO> getTemplates(
			PagingLoadConfig config) {

		ArrayList<TemplateDTO> templateDTOs = getTemplates();
		ArrayList<TemplateDTO> sublist = new ArrayList<TemplateDTO>();

		if (config.getSortInfo().size() > 0) {
			SortInfo sort = config.getSortInfo().get(0);
			if (sort.getSortField() != null) {
				final String sortField = sort.getSortField();
				if (sortField != null) {
					Collections.sort(templateDTOs, sort.getSortDir().comparator(new Comparator<TemplateDTO>() {
						public int compare(TemplateDTO t1, TemplateDTO t2) {
							if (sortField.equals("code")) {
								return t1.getCode().compareTo(t2.getCode());	
							} else if (sortField.equals("description")) {
								return t1.getDescription().compareTo(t2.getDescription());
							}
							
							return 0;
							
						}
					}));
				}
			}
		}

		int start = config.getOffset();
		int limit = templateDTOs.size();

		if (config.getLimit() > 0) {
			limit = Math.min(start + config.getLimit(), limit);
		}

		for (int i = config.getOffset(); i < limit; i++) {
			sublist.add(templateDTOs.get(i));
		}

		return new PagingLoadResultBean<TemplateDTO>(sublist,
				templateDTOs.size(), config.getOffset());

	}
	
	private TemplateDTO createTemplateDTO(Template template){
		return new TemplateDTO(template.getId(), template.getCode(), template.getDescription(), template.getSchedule());
	}
	
	public boolean templateIsUsed(TemplateDTO templateDTO) {
		
		em = JpaUtil.getEntityManager().createEntityManager();
		em.getTransaction().begin();
		Template template = new Template(templateDTO);
		ArrayList<Return> retunrs = new ArrayList<Return>(em.createQuery("SELECT e FROM Return e where e.template = :template", Return.class).setParameter("template", template).getResultList());
		
		return retunrs.size() > 0;
	}

	public ArrayList<TemplateDTO> checkTemplates(List<TemplateDTO> templateDTOs) {
		
		ArrayList<TemplateDTO> usedTemplates = new ArrayList<TemplateDTO>();
		
		for(TemplateDTO templateDTO : templateDTOs){
			if(templateIsUsed(templateDTO)){
				usedTemplates.add(templateDTO);
			}
		}
		return usedTemplates;
	}

}
