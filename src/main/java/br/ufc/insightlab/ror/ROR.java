package br.ufc.insightlab.ror;

import java.util.Iterator;
import java.util.List;

import br.ufc.insightlab.ror.entities.ResultQuery;
import br.ufc.insightlab.ror.entities.ResultQuerySet;

public interface ROR {
	ResultQuerySet runQuery(String sparqlQuery) throws Exception;
}
