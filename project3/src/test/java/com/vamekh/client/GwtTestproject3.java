package com.vamekh.client;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.vamekh.server.InstitutionServiceImpl;
import com.vamekh.server.InstitutionTypeServiceImpl;
import com.vamekh.server.ReturnServiceImpl;
import com.vamekh.server.TemplateServiceImpl;
import com.vamekh.shared.FieldVerifier;
import com.vamekh.shared.InstitutionDTO;
import com.vamekh.shared.InstitutionTypeDTO;
import com.vamekh.shared.ReturnDTO;
import com.vamekh.shared.Schedule;
import com.vamekh.shared.TemplateDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.junit.tools.GWTTestSuite;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * GWT JUnit <b>integration</b> tests must extend GWTTestCase.
 * Using <code>"GwtTest*"</code> naming pattern exclude them from running with
 * surefire during the test phase.
 * 
 * If you run the tests using the Maven command line, you will have to 
 * navigate with your browser to a specific url given by Maven. 
 * See http://mojo.codehaus.org/gwt-maven-plugin/user-guide/testing.html 
 * for details.
 */
public class GwtTestproject3 extends GWTTestSuite {

  /**
   * Must refer to a valid module that sources this class.
   */
  public String getModuleName() {
    return "com.vamekh.project3JUnit";
  }

  
  @Test
  public void addTemplateTest(){
//	  InstitutionTypeServiceImpl typeRpc = new InstitutionTypeServiceImpl();
//	  ArrayList<InstitutionTypeDTO> types = typeRpc.getInstitutionTypes();
//	  assert(types.size() > 0);
//	  
//	  InstitutionServiceImpl instRpc = new InstitutionServiceImpl();
	  TemplateServiceImpl tmpRpc = new TemplateServiceImpl();
	  TemplateDTO tmpDTO = new TemplateDTO("001", "test", Schedule.MONTHLY);
	  tmpRpc.addTemplate(tmpDTO);
	  ArrayList<TemplateDTO> templs = tmpRpc.getTemplates();
	  assert(templs.size() > 0);
//	  ReturnServiceImpl retRpc = new ReturnServiceImpl();
//	  InstitutionDTO inst = instRpc.getInstitution(7);
//	  TemplateDTO tmp = tmpRpc.getTemplate(1);
//	  ReturnDTO ret = new ReturnDTO("01", "test ret", inst, tmp);
//	  ReturnDTO retTmp = retRpc.addReturn(ret);
//	  InstitutionDTO instDTO = new InstitutionDTO("0003", "test", "", "", "", "", typeDTO, new java.sql.Date(1111111111111L));
//	  InstitutionDTO tmpDTO = instRpc.addInstitution(instDTO);
//	  assert(inst != null);
//	  assert(retTmp != null);
	  //assert(tmpDTO != null);
  }


}
