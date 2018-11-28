package br.ufc.insightlab.ror.implementations;
import java.io.File;
import java.util.logging.Logger;

import it.unibz.krdb.obda.model.OBDAException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;

import br.ufc.insightlab.ror.ROR;
import br.ufc.insightlab.ror.entities.ResultQuerySet;
/*
 * #%L
 * ontop-quest-owlapi3
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import it.unibz.krdb.obda.io.ModelIOManager;
import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.OBDAModel;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.owlrefplatform.core.QuestConstants;
import it.unibz.krdb.obda.owlrefplatform.core.QuestPreferences;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWL;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLConnection;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLFactory;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLStatement;

public class OntopROR implements ROR {
	
	/*
	 * Use the sample database using H2 from
	 * https://github.com/ontop/ontop/wiki/InstallingTutorialDatabases
	 * 
	 * Please use the pre-bundled H2 server from the above link
	 * 
	 */
	static long tempInicial;
	static long tempFinal;
	static long dif;

	private String owlfile;
	private String obdafile;
	private OWLOntologyManager manager;
	private OWLOntology ontology;
	private OBDADataFactory fac;
	private OBDAModel obdaModel;
	private ModelIOManager ioManager;
	private QuestPreferences preference;
	private QuestOWLFactory factory;
	private QuestOWL reasoner;
	private Logger log = Logger.getLogger(OntopROR.class.getName());
	
	public OntopROR(String pathOntology,String pathMapping) throws Exception{
		/*
		 * Load the ontology from an external .owl file.
		 */
//		LoggerContext context = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
//    	context.stop();
//
//    	System.setProperty("java.util.logging.SimpleFormatter.format",
//                "[%1$tc] %4$s: %5$s%n");
//
//		log.info("Starting ROR");
		
		owlfile = pathOntology;
		obdafile = pathMapping;
		manager = OWLManager.createOWLOntologyManager();
		ontology = manager.loadOntologyFromOntologyDocument(new File(owlfile));
//		log.info("Ontology loaded");
		/*
		 * Load the OBDA model from an external .obda file
		 */
		fac = OBDADataFactoryImpl.getInstance();
		obdaModel = fac.getOBDAModel();
		
		ioManager = new ModelIOManager(obdaModel);
		ioManager.load(obdafile);
//		log.info("Model loaded");
		/*
		 * Prepare the configuration for the Quest instance. The example below shows the setup for
		 * "Virtual ABox" mode
		 */
		preference = new QuestPreferences();
		preference.setCurrentValueOf(QuestPreferences.ABOX_MODE, QuestConstants.VIRTUAL);
		/*
		 * Create the instance of Quest OWL reasoner.
		 */
		factory = new QuestOWLFactory();
		factory.setOBDAController(obdaModel);
		factory.setPreferenceHolder(preference);

		reasoner = (QuestOWL) factory.createReasoner(ontology, new SimpleConfiguration());//Gera muito LOG
//		log.info("ROR is ready");
	}
	public ResultQuerySet runQuery(String sparqlQuery) throws OWLException, OBDAException {
		
		/*
		 * Prepare the data connection for querying.
		 */
//		log.info("Connecting to database");
		QuestOWLConnection conn = reasoner.getConnection();
		QuestOWLStatement st = conn.createStatement();

		return new ResultQuerySet(conn,sparqlQuery);

	}


}